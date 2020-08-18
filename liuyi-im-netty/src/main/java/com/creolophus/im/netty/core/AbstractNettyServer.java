package com.creolophus.im.netty.core;

import com.creolophus.im.netty.config.NettyServerConfig;
import com.creolophus.im.netty.exception.NettyException;
import com.creolophus.im.netty.sleuth.SleuthNettyAdapter;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.Decoder;
import com.creolophus.im.protocol.Encoder;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.liuyi.common.logger.TracerUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2020/2/24 上午10:33
 */
public class AbstractNettyServer extends AbstractNettyInstance {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyServer.class);


    private final ServerBootstrap bootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    protected NettyServerConfig nettyServerConfig;
    protected TracerUtil tracerUtil;
    protected ContextProcessor contextProcessor;
    private RequestProcessor requestProcessor;
    private ResponseProcessor responseProcessor;
    private NettyServerChannelEventListener nettyServerChannelEventListener;

    private Encoder encoder;
    private Decoder decoder;


    public AbstractNettyServer(
            NettyServerConfig nettyServerConfig,
            TracerUtil tracerUtil,
            ContextProcessor contextProcessor,
            RequestProcessor requestProcessor,
            NettyServerChannelEventListener nettyServerChannelEventListener,
            ResponseProcessor responseProcessor,
            Decoder decoder,
            Encoder encoder) {

        this.nettyServerConfig = nettyServerConfig;
        this.tracerUtil = tracerUtil;
        this.contextProcessor = contextProcessor;
        this.requestProcessor = requestProcessor;
        this.nettyServerChannelEventListener = nettyServerChannelEventListener;
        this.responseProcessor = responseProcessor;

        this.decoder = decoder;
        this.encoder = encoder;

        this.bootstrap = new ServerBootstrap(); // (2)

        if(useEpoll()) {
            this.bossGroup = new EpollEventLoopGroup(1);
            this.workerGroup = new EpollEventLoopGroup(nettyServerConfig.getServerSelectorThreads());
        } else {
            this.bossGroup = new NioEventLoopGroup(1); // (1)
            this.workerGroup = new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads());
        }
    }

    @Override
    public void response(ChannelOutboundInvoker ctx, Command response) {
        if(response != null) {
            contextProcessor.setResponse(response);
        }
        super.response(ctx, response);
    }

    @Override
    public void shutdown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void start() {
        try {
            ServerBootstrap childHandler = bootstrap.group(bossGroup, workerGroup)
                    .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
                    .childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    //new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                    new NettyConnectManageHandler(), new NettyEncoder(encoder), new NettyDecoder(decoder), new NettyServerHandler());
                        }
                    });

            if(nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
                childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            }

            ChannelFuture f = bootstrap.bind(nettyServerConfig.getListenPort()).sync(); // (7)
            logger.debug("Netty started on port {} (socket)", nettyServerConfig.getListenPort());
            f.channel().closeFuture().sync();
            logger.info("Server启动 成功 {}:{}", nettyServerConfig.getListenIp(), nettyServerConfig.getListenPort());


        } catch (InterruptedException e1) {
            throw new NettyException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }
    }

    class NettyServerHandler extends AbstractNettyHandler {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "channelRead");
            logger.debug("{}", ctx.channel());
            logger.debug(msg.toString());
            Command command = (Command) msg;
            contextProcessor.initContext(ctx.channel(), command);
            requestProcessor.verify(command);
            contextProcessor.validateAfterVerify(command);
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
            if(responseProcessor != null) {
                responseProcessor.processResponse(command);
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
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyServerChannelRegistered");
            logger.info("{}", ctx.channel());
            super.channelRegistered(ctx);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onChannelRegistered(ctx);
            }
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//            remoteContextValidator.mdc("channelUnregistered");
            logger.info("{}", ctx.channel());
            super.channelUnregistered(ctx);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onChannelUnregistered(ctx);
            }
//            remoteContextValidator.cleanContext();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            remoteContextValidator.mdc("channelActive");
            MdcUtil.setMethod("channelActive");
            logger.debug("{}", ctx.channel());
            super.channelActive(ctx);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onChannelActive(ctx);
            }
//            remoteContextValidator.cleanContext();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyServerChannelInactive");
            logger.debug("{}", ctx.channel());
            super.channelInactive(ctx);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onChannelInactive(ctx);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyServerUserEventTriggered");
            logger.debug("{} {}", ctx.channel(), evt);

            if(evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if(event.state().equals(IdleState.ALL_IDLE)) {
                    closeChannel(ctx.channel());
                }
            }

            ctx.fireUserEventTriggered(evt);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onUserEventTriggered(ctx, evt);
            }
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {
            logger.debug("{}", ctx.channel());
            logger.info("flush {}", JSON.toJSONString(contextProcessor.getResponse()));
            super.flush(ctx);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onFlush(ctx);
            }
//            remoteContextValidator.trace();
            contextProcessor.clearContext();
            SleuthNettyAdapter.getInstance().cleanContext();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            SleuthNettyAdapter.getInstance().begin(tracerUtil, "NettyServerExceptionCaught");
            ctx.fireExceptionCaught(cause);
            if(nettyServerChannelEventListener != null) {
                nettyServerChannelEventListener.onExceptionCaught(ctx, cause);
            }
        }
    }

}
