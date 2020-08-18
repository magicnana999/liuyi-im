package com.creolophus.im.netty.core;

import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.exception.NettyException;
import com.creolophus.im.netty.exception.NettyTimeoutException;
import com.creolophus.im.netty.exception.NettyTooMuchRequestException;
import com.creolophus.im.netty.sleuth.SleuthNettyAdapter;
import com.creolophus.im.netty.utils.NettyUtil;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandDecoder;
import com.creolophus.im.protocol.CommandEncoder;
import com.creolophus.liuyi.common.logger.TracerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/2/24 上午10:33
 */
public class AbstractNettyClient extends AbstractNettyInstance {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyClient.class);

    protected final ConcurrentMap<String /* commandSeq */, ResponseFuture> responseTable = new ConcurrentHashMap(256);
    /**
     * Semaphore to limit maximum number of on-going one-way requests, which protects system memory footprint.
     */
    protected final Semaphore semaphoreOneway;
    /**
     * Semaphore to limit maximum number of on-going asynchronous requests, which protects system memory footprint.
     */
    protected final Semaphore semaphoreAsync;
    private final Bootstrap bootstrap;
    private final EventLoopGroup workerGroup;
    protected NettyClientConfig nettyClientConfig;
    protected TracerUtil tracerUtil;
    private RequestProcessor requestProcessor;
    private NettyClientChannelEventListener nettyClientChannelEventListener;
    private volatile Channel channel;

    private CommandDecoder commandDecoder;
    private CommandEncoder commandEncoder;


    public AbstractNettyClient(
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil,
            RequestProcessor requestProcessor,
            NettyClientChannelEventListener nettyClientChannelEventListener, CommandDecoder commandDecoder, CommandEncoder commandEncoder) {

        this.nettyClientConfig = nettyClientConfig;
        this.tracerUtil = tracerUtil;
        this.requestProcessor = requestProcessor;
        this.nettyClientChannelEventListener = nettyClientChannelEventListener;

        this.semaphoreAsync = new Semaphore(65535);
        this.semaphoreOneway = new Semaphore(65535);

        this.commandDecoder = commandDecoder;
        this.commandEncoder = commandEncoder;

        this.bootstrap = new Bootstrap(); // (2)

        if(useEpoll()) {
            this.workerGroup = new EpollEventLoopGroup(1);
        } else {
            this.workerGroup = new NioEventLoopGroup(1);
        }
    }

    public Channel createChannel() {
        if(channel == null) {
            ChannelFuture channelFuture = this.bootstrap.connect(NettyUtil.string2SocketAddress(nettyClientConfig.getNettyAddress()));
            this.channel = channelFuture.channel();
            logger.info("Client连接 成功 {} -> {}", channel.id(), nettyClientConfig.getNettyAddress());
        }
        return channel;
    }

    private void invokeAsyncImpl(
            final Channel channel, final Command request, final long timeoutMillis, final BiConsumer<Command, Command> consumer) throws InterruptedException {
        long beginStartTime = System.currentTimeMillis();
        final String commandSeq = request.getHeader().getSeq();
        boolean acquired = this.semaphoreAsync.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if(acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreAsync);
            long costTime = System.currentTimeMillis() - beginStartTime;
            //TODO..
//            if (timeoutMillis < costTime) {
//                once.release();
//                throw new NettyTimeoutException("invokeAsync call timeout");
//            }

            final ResponseFuture responseFuture = new ResponseFuture(channel, commandSeq, timeoutMillis - costTime, consumer, once, request);
            this.responseTable.put(commandSeq, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                if(f.isSuccess()) {
                    responseFuture.setSendOk(true);
                    return;
                }
                requestFail(commandSeq, f.cause());
            });
        } else {
            throw new NettyTooMuchRequestException("invokeAsyncImpl invoke too much length:" + semaphoreAsync.getQueueLength() + ",available:" + semaphoreAsync.availablePermits());
        }
    }

    private void invokeOnewayImpl(final Channel channel, final Command request, final long timeoutMillis) throws InterruptedException {
        boolean acquired = this.semaphoreOneway.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if(acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreOneway);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                once.release();
                if(!f.isSuccess()) {
                    requestFail(request.getHeader().getSeq(), f.cause());
                }
            });
        } else {
            throw new NettyTooMuchRequestException("invokeAsyncImpl invoke too much length:" + semaphoreOneway.getQueueLength() + ",available:" + semaphoreOneway
                    .availablePermits());
        }
    }

    private Command invokeSyncImpl(final Channel channel, final Command request, final long timeoutMillis) {
        final String commandSeq = request.getHeader().getSeq();

        try {
            final ResponseFuture responseFuture = new ResponseFuture(channel, commandSeq, timeoutMillis, null, null, request);
            this.responseTable.put(commandSeq, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                if(f.isSuccess()) {
                    responseFuture.setSendOk(true);
                    return;
                } else {
                    responseFuture.setSendOk(false);
                }

                requestFail(commandSeq, f.cause());
                throw new NettyException("invokeSync error", f.cause());
            });

            Command response = responseFuture.waitResponse(timeoutMillis);
            if(null == response) {
                if(responseFuture.isSendOk()) {
                    throw new NettyTimeoutException("waitResponse timeout", responseFuture.getCause());
                } else {
                    throw new NettyException("nothing to receive for waitResponse", responseFuture.getCause());
                }
            }

            return response;
        } catch (InterruptedException e) {
            throw new NettyException("interrupted", e);
        } finally {
            this.responseTable.remove(commandSeq);
        }
    }

    private void requestFail(final String commandSeq, Throwable throwable) {
        ResponseFuture responseFuture = responseTable.remove(commandSeq);
        if(responseFuture != null) {
            responseFuture.setSendOk(false);
            responseFuture.putResponse(null);
            responseFuture.release();
        }

        if(throwable != null) {
            throw new NettyException("Send request error", throwable);
        }
    }

    @Override
    public void response(ChannelOutboundInvoker ctx, Command response) {
        logger.debug(response.toString());
        super.response(ctx, response);
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
    }

    @Override
    public void start() {
        Bootstrap childHandler = bootstrap.group(workerGroup)
                .channel(useEpoll() ? EpollSocketChannel.class : NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_SNDBUF, nettyClientConfig.getClientSocketSndBufSize())
                .option(ChannelOption.SO_RCVBUF, nettyClientConfig.getClientSocketRcvBufSize())
                .handler(new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                                //new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                new NettyConnectManageHandler(), new NettyEncoder(commandEncoder), new NettyDecoder(commandDecoder), new NettyClientHandler());
                    }
                });
        logger.info("Client启动 成功");

    }

    public void sendAsync(Command request, long timeoutMillis, BiConsumer<Command, Command> consumer) throws InterruptedException {
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = this.createChannel();
        if(channel != null && channel.isActive()) {
            long costTime = System.currentTimeMillis() - beginStartTime;
//                if (timeoutMillis < costTime) {
//                    throw new NettyTimeoutException("invokeAsync call timeout");
//                }
            this.invokeAsyncImpl(channel, request, timeoutMillis - costTime, consumer);
        } else {
            this.closeChannel(channel);
            throw new NettyException("channel could not be used");
        }
    }

    public void sendOneway(Command request, long timeoutMillis) throws InterruptedException {
        final Channel channel = this.createChannel();
        if(channel != null && channel.isActive()) {
            this.invokeOnewayImpl(channel, request, timeoutMillis);
        } else {
            this.closeChannel(channel);
            throw new NettyException("channel could not be used");
        }
    }

    public Command sendSync(Command request, long timeoutMillis) {
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = createChannel();
        if(channel != null && channel.isActive()) {

            long costTime = System.currentTimeMillis() - beginStartTime;
            if(timeoutMillis < costTime) {
                throw new NettyTimeoutException("sendSync call timeout");
            }
            return this.invokeSyncImpl(channel, request, timeoutMillis - costTime);
        } else {
            this.closeChannel(channel);
            throw new NettyException("Channel不可用");
        }
    }

    class NettyClientHandler extends AbstractNettyHandler {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "channelRead");
            logger.debug("{}", ctx.channel());
            logger.debug(msg.toString());
            super.channelRead(ctx, msg);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Command msg) {
            if(msg.getHeader().getCode() == 0) {
                processRequestCommand(ctx, msg);
            } else {
                processResponseCommand(ctx, msg);
            }
        }

        @Override
        protected RequestProcessor getRequestProcessor() {
            return requestProcessor;
        }

        protected void processRequestCommand(ChannelHandlerContext ctx, Command command) {
            Command response = handleRequest(command);
            response(ctx, response);
        }

        protected void processResponseCommand(ChannelHandlerContext ctx, Command command) {

            final String commandSeq = command.getHeader().getSeq();
            final ResponseFuture responseFuture = responseTable.get(commandSeq);
            if(responseFuture != null) {
                responseTable.remove(commandSeq);
                responseFuture.putResponse(command);
                responseFuture.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

            Command response = handleExceptionCaught(ctx, cause);
            response(ctx, response);
        }


    }

    class NettyConnectManageHandler extends ChannelDuplexHandler {

        private Logger logger = LoggerFactory.getLogger(NettyConnectManageHandler.class);

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyClientConnect");
            super.connect(ctx, remoteAddress, localAddress, promise);
            logger.info("{}", ctx.channel());
            if(nettyClientChannelEventListener != null) {
                nettyClientChannelEventListener.onConnect(ctx, remoteAddress, localAddress, promise);
            }
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyClientDisConnect");
            closeChannel(ctx.channel());
            super.disconnect(ctx, promise);
            logger.info("{}", ctx.channel());
            if(nettyClientChannelEventListener != null) {
                nettyClientChannelEventListener.onDisconnect(ctx, promise);
            }
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyClientClose");
//            closeChannel(ctx.channel());
            super.close(ctx, promise);
            logger.info("{}", ctx.channel());
            if(nettyClientChannelEventListener != null) {
                nettyClientChannelEventListener.onClose(ctx, promise);
            }
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            super.flush(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyClientEventTriggered");
            logger.debug("{} {}", ctx.channel(), evt);

            if(evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if(event.state().equals(IdleState.ALL_IDLE)) {
                    closeChannel(ctx.channel());
                }
            }

            ctx.fireUserEventTriggered(evt);
            if(nettyClientChannelEventListener != null) {
                nettyClientChannelEventListener.onUserEventTriggered(ctx, evt);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyClientExceptionCaught");
            ctx.fireExceptionCaught(cause);
            if(nettyClientChannelEventListener != null) {
                nettyClientChannelEventListener.onExceptionCaught(ctx, cause);
            }
        }
    }

}
