package com.creolophus.liuyi.netty.config;

import java.nio.charset.Charset;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:37
 */
public class NettyServerConfig implements Cloneable {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    private int listenPort = 8888;
    private String listenIp = "127.0.0.1";
    private int serverSelectorThreads = Runtime.getRuntime().availableProcessors();
    private int serverChannelMaxIdleTimeSeconds = 120;

    private int serverSocketSndBufSize = 65535;
    private int serverSocketRcvBufSize = 65535;
    private boolean serverPooledByteBufAllocatorEnable = true;

    private boolean useEpollNativeSelector = true;

    private long serverPushTimeoutMillions = 300;

    private int frameMaxLength = 128 * 1024;

    public int getListenPort() {
        return listenPort;
    }
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getListenIp() {
        return listenIp;
    }
    public void setListenIp(String listenIp) {
        this.listenIp = listenIp;
    }

    public int getServerSelectorThreads() {
        return serverSelectorThreads;
    }
    public void setServerSelectorThreads(int serverSelectorThreads) {
        this.serverSelectorThreads = serverSelectorThreads;
    }

    public int getServerChannelMaxIdleTimeSeconds() {
        return serverChannelMaxIdleTimeSeconds;
    }
    public void setServerChannelMaxIdleTimeSeconds(int serverChannelMaxIdleTimeSeconds) {
        this.serverChannelMaxIdleTimeSeconds = serverChannelMaxIdleTimeSeconds;
    }

    public int getServerSocketSndBufSize() {
        return serverSocketSndBufSize;
    }
    public void setServerSocketSndBufSize(int serverSocketSndBufSize) {
        this.serverSocketSndBufSize = serverSocketSndBufSize;
    }

    public int getServerSocketRcvBufSize() {
        return serverSocketRcvBufSize;
    }
    public void setServerSocketRcvBufSize(int serverSocketRcvBufSize) {
        this.serverSocketRcvBufSize = serverSocketRcvBufSize;
    }

    public boolean isServerPooledByteBufAllocatorEnable() {
        return serverPooledByteBufAllocatorEnable;
    }
    public void setServerPooledByteBufAllocatorEnable(boolean serverPooledByteBufAllocatorEnable) {
        this.serverPooledByteBufAllocatorEnable = serverPooledByteBufAllocatorEnable;
    }

    public boolean isUseEpollNativeSelector() {
        return useEpollNativeSelector;
    }
    public void setUseEpollNativeSelector(boolean useEpollNativeSelector) {
        this.useEpollNativeSelector = useEpollNativeSelector;
    }

    public long getServerPushTimeoutMillions() {
        return serverPushTimeoutMillions;
    }
    public void setServerPushTimeoutMillions(long serverPushTimeoutMillions) {
        this.serverPushTimeoutMillions = serverPushTimeoutMillions;
    }

    public int getFrameMaxLength() {
        return frameMaxLength;
    }
    public void setFrameMaxLength(int frameMaxLength) {
        this.frameMaxLength = frameMaxLength;
    }
}
