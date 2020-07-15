package com.creolophus.liuyi.domain;

import com.creolophus.liuyi.netty.utils.NettyUtil;
import io.netty.channel.Channel;

/**
 * @author magicnana
 * @date 2020/1/11 下午2:45
 */
public class UserChannel extends UserClient{

    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public static String getChannelId(Channel channel) {
        return NettyUtil.getChannelId(channel);
    }

    public String getChannelId() {
        return getChannelId(getChannel());
    }

    @Override
    public String toString() {
        return "{\"userId\":"+getUserId()+",\"appKey\":"+getAppKey()+",\"channel\":"+getChannelId()+"}";
    }
}
