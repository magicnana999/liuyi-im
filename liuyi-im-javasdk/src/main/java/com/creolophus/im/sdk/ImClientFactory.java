package com.creolophus.im.sdk;


import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.liuyi.common.logger.TracerUtil;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {


    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener, NettyImClient.MessageReceiver messageReceiver, MessageCoder messageCoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, messageCoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig, TracerUtil tracerUtil, MessageCoder messageCoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, tracerUtil, messageCoder)
                        .start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver, TracerUtil tracerUtil, MessageCoder messageCoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, tracerUtil, messageCoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig,
            MessageCoder messageCoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, messageCoder).start();
            }
        }
        return nettyClient;
    }


}
