package com.creolophus.im.sdk;


import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.ChannelEventListener;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.netty.core.ResponseProcessor;
import com.creolophus.liuyi.common.logger.TracerUtil;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {

    private static LiuyiImClient socketClient;

    private static LiuyiImClient nettyClient;

    public static LiuyiImClient getSocketImClient(Configration configration,PushProcessor pushProcessor){
        if(socketClient==null){
            synchronized (LiuyiImClient.class){
                socketClient = new SocketImClient(configration,pushProcessor);
            }
        }
        return socketClient;
    }

    public static LiuyiImClient getNettyClient(NettyClientConfig nettyClientConfig,
                                               ContextProcessor contextProcessor,
                                               RequestProcessor requestProcessor,
                                               ChannelEventListener channelEventListener,
                                               ResponseProcessor responseProcessor){
        if(nettyClient==null){
            synchronized (LiuyiImClient.class){
                nettyClient = new NettyImClient(nettyClientConfig, contextProcessor, requestProcessor, channelEventListener, responseProcessor);
            }
        }
        return nettyClient;
    }


}
