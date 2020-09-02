package com.creolophus.im.sdk;


import com.creolophus.im.coder.MessageCoderSelector;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {


    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener, MessageReceiver messageReceiver, MessageCoderSelector messageCoderSelector) {
        if(nettyClient == null) {
            synchronized (LiuyiImClient.class) {
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, messageCoderSelector);
            }
        }
        return nettyClient;
    }


}
