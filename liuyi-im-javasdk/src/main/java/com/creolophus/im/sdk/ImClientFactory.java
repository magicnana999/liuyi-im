package com.creolophus.im.sdk;


import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.protocol.Decoder;
import com.creolophus.im.protocol.Encoder;
import com.creolophus.liuyi.common.logger.TracerUtil;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {


    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener, NettyImClient.MessageReceiver messageReceiver, Decoder decoder, Encoder encoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, decoder, encoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil,
            Decoder decoder,
            Encoder encoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, tracerUtil, decoder, encoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            TracerUtil tracerUtil,
            Decoder decoder,
            Encoder encoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, tracerUtil, decoder, encoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig,
            Decoder decoder,
            Encoder encoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, decoder, encoder).start();
            }
        }
        return nettyClient;
    }


}
