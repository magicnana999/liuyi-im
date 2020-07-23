package com.creolophus.im.netty.core;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author magicnana
 * @date 2020/6/30 下午2:48
 */
public interface ChannelEventListener {
    void onChannelActive(ChannelHandlerContext ctx);

    void onChannelInactive(ChannelHandlerContext ctx);

    void onChannelRegistered(ChannelHandlerContext ctx);

    void onChannelUnregistered(ChannelHandlerContext ctx);

    void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause);

    void onFlush(ChannelHandlerContext ctx);

    void onUserEventTriggered(ChannelHandlerContext ctx, Object evt);
}
