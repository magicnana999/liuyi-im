package com.creolophus.im.sdk;

import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.*;
import com.creolophus.im.protocol.Command;

import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class NettyImClient extends AbstractImClient implements LiuyiImClient {

    private AbstractNettyClient abstractNettyClient;

    NettyImClient(
            NettyClientConfig config,
            ContextProcessor cp,
            RequestProcessor reqp,
            ChannelEventListener listener,
            ResponseProcessor resp) {
        abstractNettyClient = new AbstractNettyClient(config, null, cp,reqp ,listener,resp);
    }

    @Override
    public void close() {
        abstractNettyClient.shutdown();
    }

    @Override
    public void sendMessage(Command request, BiConsumer<Command, Command> consumer) {
        try {
            abstractNettyClient.sendAsync(request, 100, consumer);
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
    }
}
