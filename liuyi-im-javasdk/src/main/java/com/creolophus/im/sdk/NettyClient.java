package com.creolophus.im.sdk;

import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.*;
import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.logger.TracerUtil;
import io.netty.channel.Channel;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * @author magicnana
 * @date 2020/8/4 上午11:14
 */
public class NettyClient extends AbstractNettyClient {
    private static final int tSize = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService es = new ThreadPoolExecutor(tSize, tSize, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private static final ConcurrentHashMap<String, AckProcessor> requestTable = new ConcurrentHashMap<>(128);
    private static final LinkedBlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>(128);

    private volatile int writeSize = 0;

    protected Configration configration;

    private Channel channel;
    private Selector selector;

    private PushProcessor pushProcessor;

    private boolean isConnect = false;

    public NettyClient(
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil,
            ContextProcessor contextProcessor,
            RequestProcessor requestProcessor,
            ChannelEventListener channelEventListener,
            ResponseProcessor responseProcessor) {
        super(nettyClientConfig, tracerUtil, contextProcessor, requestProcessor, channelEventListener, responseProcessor);
    }

    protected boolean isConnect(){
        return isConnect;
    }
}
