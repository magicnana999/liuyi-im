package com.creolophus.im.netty.core;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.exception.*;
import com.creolophus.im.netty.sleuth.SleuthNettyAdapter;
import com.creolophus.im.netty.utils.NettyUtil;
import com.creolophus.im.netty.utils.OS;
import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.logger.TracerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    private final Bootstrap bootstrap;
    private final EventLoopGroup workerGroup;

    protected NettyClientConfig nettyClientConfig;
    protected TracerUtil tracerUtil;
    protected ContextProcessor contextProcessor;
    private RequestProcessor requestProcessor;
    private ResponseProcessor responseProcessor;
    private ChannelEventListener channelEventListener;

    private volatile Channel channel;


    /**
     * Semaphore to limit maximum number of on-going one-way requests, which protects system memory footprint.
     */
    protected final Semaphore semaphoreOneway;

    /**
     * Semaphore to limit maximum number of on-going asynchronous requests, which protects system memory footprint.
     */
    protected final Semaphore semaphoreAsync;


    public AbstractNettyClient(
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil,
            ContextProcessor contextProcessor,
            RequestProcessor requestProcessor,
            ChannelEventListener channelEventListener,
            ResponseProcessor responseProcessor) {

        this.nettyClientConfig= nettyClientConfig;
        this.tracerUtil = tracerUtil;
        this.contextProcessor = contextProcessor;
        this.requestProcessor = requestProcessor;
        this.channelEventListener = channelEventListener;
        this.responseProcessor = responseProcessor;

        this.semaphoreAsync = new Semaphore(65535);
        this.semaphoreOneway = new Semaphore(65535);

        this.bootstrap = new Bootstrap(); // (2)

        if(useEpoll()) {
            this.workerGroup = new EpollEventLoopGroup(1);
        } else {
            this.workerGroup = new NioEventLoopGroup(1);
        }
    }

    private Channel createChannel() {
        if (channel==null) {
            ChannelFuture channelFuture = this.bootstrap.connect(NettyUtil.string2SocketAddress(nettyClientConfig.getNettyAddress()));
            this.channel = channelFuture.channel();
        }
        return channel;
    }

    private Command invokeSyncImpl(final Channel channel, final Command request, final long timeoutMillis) {
        final String commandSeq = request.getHeader().getSeq();

        try {
            final ResponseFuture responseFuture = new ResponseFuture(channel, commandSeq, timeoutMillis, null, null);
            this.responseTable.put(commandSeq, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    responseFuture.setSendOk(true);
                    return;
                } else {
                    responseFuture.setSendOk(false);
                }

                requestFail(commandSeq,f.cause());
                throw new NettyException("invokeSync error", f.cause());
            });

            Command response = responseFuture.waitResponse(timeoutMillis);
            if (null == response) {
                if (responseFuture.isSendOk()) {
                    throw new NettyTimeoutException("waitResponse timeout",responseFuture.getCause());
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

    public Command sendSync(Command request, long timeoutMillis) {
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = createChannel();
        if (channel != null && channel.isActive()) {

            long costTime = System.currentTimeMillis() - beginStartTime;
            if (timeoutMillis < costTime) {
                throw new NettyTimeoutException("sendSync call timeout");
            }
            return this.invokeSyncImpl(channel, request, timeoutMillis - costTime);
        } else {
            this.closeChannel(channel);
            throw new NettyException("Channel不可用");
        }
    }

    private void invokeAsyncImpl(final Channel channel, final Command request, final long timeoutMillis, final BiConsumer<Command,Command> consumer) throws InterruptedException {
        long beginStartTime = System.currentTimeMillis();
        final String commandSeq = request.getHeader().getSeq();
        boolean acquired = this.semaphoreAsync.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreAsync);
            long costTime = System.currentTimeMillis() - beginStartTime;
            if (timeoutMillis < costTime) {
                once.release();
                throw new NettyTimeoutException("invokeAsync call timeout");
            }

            final ResponseFuture responseFuture = new ResponseFuture(channel, commandSeq, timeoutMillis - costTime, consumer, once);
            this.responseTable.put(commandSeq, responseFuture);
            channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                if (f.isSuccess()) {
                    responseFuture.setSendOk(true);
                    return;
                }
                requestFail(commandSeq,f.cause());
            });
        } else {
                throw new NettyTooMuchRequestException("invokeAsyncImpl invoke too much length:"+semaphoreAsync.getQueueLength()+",available:"+semaphoreAsync.availablePermits());
        }
    }

    private void invokeOnewayImpl(final Channel channel, final Command request, final long timeoutMillis) throws InterruptedException{
        boolean acquired = this.semaphoreOneway.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreOneway);
                channel.writeAndFlush(request).addListener((ChannelFutureListener) f -> {
                    once.release();
                    if (!f.isSuccess()) {
                        requestFail(request.getHeader().getSeq(),f.cause());
                    }
                });
        } else {
            throw new NettyTooMuchRequestException("invokeAsyncImpl invoke too much length:"+semaphoreOneway.getQueueLength()+",available:"+semaphoreOneway.availablePermits());
        }
    }

    public void sendAsync(Command request, long timeoutMillis, BiConsumer<Command,Command> consumer) throws InterruptedException {
        long beginStartTime = System.currentTimeMillis();
        final Channel channel = this.createChannel();
        if (channel != null && channel.isActive()) {
                long costTime = System.currentTimeMillis() - beginStartTime;
                if (timeoutMillis < costTime) {
                    throw new NettyTimeoutException("invokeAsync call timeout");
                }
                this.invokeAsyncImpl(channel, request, timeoutMillis - costTime, consumer);
        } else {
            this.closeChannel(channel);
            throw new NettyException("channel could not be used");
        }
    }

    public void sendOneway(Command request, long timeoutMillis) throws InterruptedException{
        final Channel channel = this.createChannel();
        if (channel != null && channel.isActive()) {
            this.invokeOnewayImpl(channel, request, timeoutMillis);
        } else {
            this.closeChannel(channel);
            throw new NettyException("channel could not be used");
        }
    }

    private void requestFail(final String commandSeq,Throwable throwable) {
        ResponseFuture responseFuture = responseTable.remove(commandSeq);
        if (responseFuture != null) {
            responseFuture.setSendOk(false);
            responseFuture.putResponse(null);
            responseFuture.release();
        }

        if(throwable!=null){
            throw new NettyException("Send request error", throwable);
        }
    }

    public void response(ChannelOutboundInvoker ctx, Command response) {
        if(response != null) {
//            remoteContextValidator.setResponse(response);
            contextProcessor.setResponse(response);
            try {
                ctx.writeAndFlush(response);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Nothing to response");
        }
    }

    protected void closeChannel(Channel channel) {
        channel.close().addListener((ChannelFutureListener) future -> logger.debug("channel has been closed {}",channel));
    }


    private boolean useEpoll() {
        return OS.isLinux() && Epoll.isAvailable();
    }

    @Override
    public void shutdown() {
        workerGroup.shutdownGracefully();
    }

    @Override
    public void start() {
        Bootstrap childHandler = bootstrap.group(workerGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_SNDBUF, nettyClientConfig.getClientSocketSndBufSize())
                .option(ChannelOption.SO_RCVBUF, nettyClientConfig.getClientSocketRcvBufSize())
                .handler(new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(
                                        //new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                        new NettyConnectManageHandler(),
                                        new NettyEncoder(),
                                        new NettyDecoder(),
                                        new NettyClientHandler());
                    }});
    }

    class NettyClientHandler extends SimpleChannelInboundHandler<Command> {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//            remoteContextValidator.begin("exceptionCaught");
            logger.error("{} {} {}",ctx.channel(),cause.getClass().getSimpleName(),cause.getMessage(),cause);

            Command response = null;

            if(cause!=null && cause instanceof NettyCommandWithResException){
                NettyCommandWithResException e = (NettyCommandWithResException)cause;
                response = e.getResponse();
            }

            else if(cause!=null && cause instanceof NettyCommandException){
                NettyCommandException e = (NettyCommandException)cause;
                response = Command.newResponse(
//                        RemoteContext.getContext().getRequest().getOpaque(),
//                        RemoteContext.getContext().getRequest().getHeader().getCode(),
                        "100",
                        12,
                        e.getNettyError());
            }

            else if(cause!= null && cause instanceof DecoderException){
                response = Command.newResponse(
                        "0",
                        0,
                        NettyError.E_REQUEST_DECODE_FAIL);
            }

            else{
                response = Command.newResponse(
//                        RemoteContext.getContext().getRequest().getOpaque(),
//                        RemoteContext.getContext().getRequest().getHeader().getCode(),
                        "100",
                        12,
                        NettyError.E_ERROR);
            }

            response(ctx, response);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil,"channelRead" );
            logger.debug("{}",ctx.channel());
            Command command = (Command)msg;
//            remoteContextValidator.initContext(ctx.channel(), command);
            contextProcessor.initContext(ctx.channel(), command);
            super.channelRead(ctx, msg);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Command msg) {
            if(msg.getHeader().getCode()==0){
                processRequestCommand(ctx,msg);
            }else{
                processResponseCommand(ctx,msg);
            }
        }

        protected void processRequestCommand(ChannelHandlerContext ctx,Command command){
            Command response = process(ctx, command);
            response(ctx, response);
        }

        protected void processResponseCommand(ChannelHandlerContext ctx,Command command){

            final String commandSeq = command.getHeader().getSeq();
            final ResponseFuture responseFuture = responseTable.get(commandSeq);
            if (responseFuture != null) {
                responseTable.remove(commandSeq);
                responseFuture.putResponse(command);
                responseFuture.release();
            }

            if(responseProcessor!=null){
                responseProcessor.processResponse(command);
            }
        }

        protected Command process(ChannelHandlerContext ctx,Command cmd){
            Command response;
            try {
                final Object requestReturn = requestProcessor.processRequest(cmd);
                response = Command.newResponse(cmd.getHeader().getSeq(), cmd.getHeader().getType(), requestReturn);
            }catch (NettyCommandWithResException e){
                logger.error(e.getMessage(),e);
                response = e.getResponse();
            } catch (NettyCommandException e) {
                logger.error(e.getMessage(),e);
                response = Command.newResponse(cmd.getHeader().getSeq(), cmd.getHeader().getType(), e.getNettyError());
            } catch (Throwable e) {
                logger.error(e.getMessage(),e);
                response = Command.newResponse(cmd.getHeader().getSeq(), cmd.getHeader().getType(),NettyError.E_ERROR);
            }
            return response;
        }
    }

    class NettyConnectManageHandler extends ChannelDuplexHandler {

        private Logger logger = LoggerFactory.getLogger(NettyConnectManageHandler.class);

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            logger.debug("{}",ctx.channel());
            logger.info("flush {}", JSON.toJSONString(contextProcessor.getResponse()));
            super.flush(ctx);
            if(channelEventListener!=null){
                channelEventListener.onFlush(ctx);
            }
//            remoteContextValidator.trace();
            contextProcessor.clearContext();
            SleuthNettyAdapter.getInstance().cleanContext();
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil,"channelRegistered");
            logger.info("{}",ctx.channel());
            super.channelRegistered(ctx);
            if(channelEventListener!=null){
                channelEventListener.onChannelRegistered(ctx);
            }
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//            remoteContextValidator.mdc("channelUnregistered");
            logger.info("{}",ctx.channel());
            super.channelUnregistered(ctx);
            if(channelEventListener!=null){
                channelEventListener.onChannelUnregistered(ctx);
            }
//            remoteContextValidator.cleanContext();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            remoteContextValidator.mdc("channelActive");
            MdcUtil.setMethod("channelActive");
            logger.debug("{}",ctx.channel());
            super.channelActive(ctx);
            if(channelEventListener!=null){
                channelEventListener.onChannelActive(ctx);
            }
//            remoteContextValidator.cleanContext();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil,"channelInactive");
            logger.debug("{}",ctx.channel());
            super.channelInactive(ctx);
            if(channelEventListener!=null){
                channelEventListener.onChannelInactive(ctx);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil,"userEventTriggered");
            logger.debug("{} {}",ctx.channel(),evt);

            if(evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if(event.state().equals(IdleState.ALL_IDLE)) {
                    closeChannel(ctx.channel());
                }
            }

            ctx.fireUserEventTriggered(evt);
            if(channelEventListener!=null){
                channelEventListener.onUserEventTriggered(ctx,evt);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil,"exceptionCaught");
            ctx.fireExceptionCaught(cause);
            if(channelEventListener!=null){
                channelEventListener.onExceptionCaught(ctx,cause);
            }
        }
    }

}
