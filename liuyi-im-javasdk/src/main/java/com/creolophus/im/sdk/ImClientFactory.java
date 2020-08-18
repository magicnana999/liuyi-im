package com.creolophus.im.sdk;


import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.protocol.CommandDecoder;
import com.creolophus.im.protocol.CommandEncoder;
import com.creolophus.liuyi.common.logger.TracerUtil;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {


    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            CommandDecoder commandDecoder,
            CommandEncoder commandEncoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, commandDecoder, commandEncoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil, CommandDecoder commandDecoder, CommandEncoder commandEncoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, tracerUtil, commandDecoder, commandEncoder)
                        .start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            TracerUtil tracerUtil, CommandDecoder commandDecoder, CommandEncoder commandEncoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, tracerUtil, commandDecoder, commandEncoder).start();
            }
        }
        return nettyClient;
    }

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener,
            NettyImClient.MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig, CommandDecoder commandDecoder, CommandEncoder commandEncoder) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, nettyClientConfig, commandDecoder, commandEncoder).start();
            }
        }
        return nettyClient;
    }


}
