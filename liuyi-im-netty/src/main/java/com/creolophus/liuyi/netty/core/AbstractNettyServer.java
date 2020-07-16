package com.creolophus.liuyi.netty.core;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.logger.TracerUtil;
import com.creolophus.liuyi.netty.config.NettyServerConfig;
import com.creolophus.liuyi.netty.exception.NettyCommandException;
import com.creolophus.liuyi.netty.exception.NettyCommandWithResException;
import com.creolophus.liuyi.netty.exception.NettyError;
import com.creolophus.liuyi.netty.exception.NettyException;
import com.creolophus.liuyi.netty.protocol.Command;
import com.creolophus.liuyi.netty.sleuth.SleuthNettyAdapter;
import com.creolophus.liuyi.netty.utils.OS;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
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
    private ChannelEventListener channelEventListener;


    public AbstractNettyServer(
            NettyServerConfig nettyServerConfig,
            TracerUtil tracerUtil,
            ContextProcessor contextProcessor,
            RequestProcessor requestProcessor,
            ChannelEventListener channelEventListener) {

        this.nettyServerConfig= nettyServerConfig;
        this.tracerUtil = tracerUtil;
        this.contextProcessor = contextProcessor;
        this.requestProcessor = requestProcessor;
        this.channelEventListener = channelEventListener;

        this.bootstrap = new ServerBootstrap(); // (2)

        if(useEpoll()) {
            this.bossGroup = new EpollEventLoopGroup(1);
            this.workerGroup = new EpollEventLoopGroup(nettyServerConfig.getServerSelectorThreads());
        } else {
            this.bossGroup = new NioEventLoopGroup(1); // (1)
            this.workerGroup = new NioEventLoopGroup(nettyServerConfig.getServerSelectorThreads());
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
                            ch.pipeline()
                                    .addLast(
                                            //new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                            new NettyConnectManageHandler(),
                                            new NettyEncoder(),
                                            new NettyDecoder(),
                                            new NettyServerHandler());
                        }});

            if(nettyServerConfig.isServerPooledByteBufAllocatorEnable()) {
                childHandler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            }

            ChannelFuture f = bootstrap.bind(nettyServerConfig.getListenPort()).sync(); // (7)
            logger.info("Netty started on port {} (socket)", nettyServerConfig.getListenPort());
            f.channel().closeFuture().sync();

        } catch (InterruptedException e1) {
            throw new NettyException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }
    }

    class NettyServerHandler extends SimpleChannelInboundHandler<Command> {

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
                        NettyError.E_REQUEST_BODY_DECODE_ERROR);
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
            requestProcessor.verify(command);
            contextProcessor.validateUserId(command);
            super.channelRead(ctx, msg);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Command msg) {
            Command response = process(ctx, msg);
            response(ctx, response);
        }

        protected Command process(ChannelHandlerContext ctx,Command cmd){
            Command response;
            try {
                final Object requestReturn = requestProcessor.processRequest(cmd);
                response = Command.newResponse(cmd.getOpaque(), cmd.getHeader().getType(), requestReturn);
            }catch (NettyCommandWithResException e){
                logger.error(e.getMessage(),e);
                response = e.getResponse();
            } catch (NettyCommandException e) {
                logger.error(e.getMessage(),e);
                response = Command.newResponse(cmd.getOpaque(), cmd.getHeader().getType(), e.getNettyError());
            } catch (Throwable e) {
                logger.error(e.getMessage(),e);
                response = Command.newResponse(cmd.getOpaque(), cmd.getHeader().getType(),NettyError.E_ERROR);
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
