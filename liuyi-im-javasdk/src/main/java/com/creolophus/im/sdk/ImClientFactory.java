package com.creolophus.im.sdk;


import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {


    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getNettyClient(
            NettyClientChannelEventListener nettyClientChannelEventListener, NettyImClient.MessageReceiver messageReceiver, MessageCoder messageCoder) {
        if(nettyClient==null){
            synchronized (LiuyiImClient.class){
                nettyClient = new NettyImClient(nettyClientChannelEventListener, messageReceiver, messageCoder).start();
            }
        }
        return nettyClient;
    }


}
