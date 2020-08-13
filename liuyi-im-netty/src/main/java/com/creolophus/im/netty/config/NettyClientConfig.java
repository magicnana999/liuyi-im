package com.creolophus.im.netty.config;

import java.nio.charset.Charset;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:37
 */
public class NettyClientConfig implements Cloneable {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    private int clientSocketSndBufSize = 65535;
    private int clientSocketRcvBufSize = 65535;

    public int getClientSocketRcvBufSize() {
        return clientSocketRcvBufSize;
    }

    public void setClientSocketRcvBufSize(int clientSocketRcvBufSize) {
        this.clientSocketRcvBufSize = clientSocketRcvBufSize;
    }

    public int getClientSocketSndBufSize() {
        return clientSocketSndBufSize;
    }

    public void setClientSocketSndBufSize(int clientSocketSndBufSize) {
        this.clientSocketSndBufSize = clientSocketSndBufSize;
    }

    public String getNettyAddress() {
        return "127.0.0.1:33009";
    }
}
