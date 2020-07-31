package com.creolophus.im.sdk;


/**
 * @author magicnana
 * @date 2020/7/30 上午10:46
 */
public class ImClientFactory {

    private static LiuyiImClient instance;

    public static LiuyiImClient getInstance(Configration configration,PushProcessor pushProcessor){
        if(instance==null){
            synchronized (LiuyiImClient.class){
                instance = new DefaultImClientImpl(configration,pushProcessor);
            }
        }
        return instance;
    }
}
