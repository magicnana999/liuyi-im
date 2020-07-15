package com.creolophus.liuyi.netty.utils;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 下午2:06
 */
public class NettyUtil {

    public static IPAndPort parseChannelIpAndPort(Channel channel) {
        return socketAddress2IpAndPort(channel.remoteAddress());
    }

    public static String parseChannelRemoteAddr(Channel channel) {
        return socketAddress2String(channel.remoteAddress());
    }

    public static SocketAddress string2SocketAddress(final String addr) {
        String[] s = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
        return isa;
    }

    public static String socketAddress2String(final SocketAddress addr) {
        StringBuilder sb = new StringBuilder();
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        sb.append(inetSocketAddress.getAddress().getHostAddress());
        sb.append(":");
        sb.append(inetSocketAddress.getPort());
        return sb.toString();
    }

    public static IPAndPort socketAddress2IpAndPort(final SocketAddress addr) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) addr;
        return new IPAndPort(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
    }

    public static String getChannelId(Channel channel){
        return channel==null?null:channel.id().asShortText();
    }

}
