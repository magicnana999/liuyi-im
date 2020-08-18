package com.creolophus.im.netty;

import com.creolophus.im.domain.UserChannel;
import com.creolophus.im.netty.core.NettyServerChannelEventListener;
import com.creolophus.im.service.UserChannelClientService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/6/30 下午2:59
 */
@Service
public class DefaultNettyServerChannelEventListener implements NettyServerChannelEventListener {

    @Resource
    private UserChannelClientService userChannelClientService;


    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelRegistered(ChannelHandlerContext ctx) {

    }

    @Override
    public void onChannelUnregistered(ChannelHandlerContext ctx) {
        unregisterUserClient(ctx.channel());
    }

    @Override
    public void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void onFlush(ChannelHandlerContext ctx) {

    }

    @Override
    public void onUserEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    @Async
    public void unregisterUserClient(Channel channel) {
        UserChannel uc = userChannelClientService.getUserClient(UserChannel.getChannelId(channel));
        if(uc != null) {
            userChannelClientService.unregisterUserClient(uc);
        }
    }
}
