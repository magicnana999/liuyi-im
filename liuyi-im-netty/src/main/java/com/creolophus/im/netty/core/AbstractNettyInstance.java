package com.creolophus.im.netty.core;

import com.creolophus.im.netty.utils.OS;
import com.creolophus.im.protocol.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.epoll.Epoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNettyInstance {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyInstance.class);


    public abstract void start();

    public abstract void shutdown();

    public void response(ChannelOutboundInvoker ctx, Command response) {
        if(response != null) {
            try {
                ctx.writeAndFlush(response);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void closeChannel(Channel channel) {
        channel.close().addListener((ChannelFutureListener) future -> logger.debug("channel has been closed {}",channel));
    }

    public boolean useEpoll() {
        return OS.isLinux() && Epoll.isAvailable();
    }


}