package com.creolophus.im.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author magicnana
 * @date 2020/2/21 下午6:38
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = Integer.parseInt("39999");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static class TimeClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf byteBuf = (ByteBuf) msg;
            try {
                byte[] result = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(result);
                String text = new String(result);
                System.out.println("收到服务端消息:" + text);
            } finally {
                byteBuf.release();
            }
            final ByteBuf time = ctx.alloc().buffer(8);
            time.writeBytes("我已经收到了，谢谢你。".getBytes());
            final ChannelFuture f = ctx.writeAndFlush(time);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
            System.out.println("TimeClientHandler:exceptionCaught");
        }
    }
}
