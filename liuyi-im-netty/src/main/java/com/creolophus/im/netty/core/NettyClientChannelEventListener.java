package com.creolophus.im.netty.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author magicnana
 * @date 2020/6/30 下午2:48
 */
public interface NettyClientChannelEventListener {

    void onClose(ChannelHandlerContext ctx, ChannelPromise promise);

    void onConnect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise);

    void onDisconnect(ChannelHandlerContext ctx, ChannelPromise promise);

    void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause);

    void onUserEventTriggered(ChannelHandlerContext ctx, Object evt);

}
