package com.creolophus.im.sdk;

import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.AbstractNettyClient;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.type.PushMessageAck;
import com.creolophus.liuyi.common.logger.TracerUtil;

import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class NettyImClient extends AbstractImClient implements LiuyiImClient, RequestProcessor {

    private AbstractNettyClient abstractNettyClient;
    private MessageReceiver messageReceiver;

    NettyImClient(MessageCoder messageCoder) {
        super(messageCoder);
    }

    NettyImClient(NettyClientChannelEventListener listener, MessageReceiver messageReceiver, MessageCoder messageCoder) {
        super(messageCoder);
        abstractNettyClient = new AbstractNettyClient(new NettyClientConfig(), null, this, listener, messageCoder);
        this.messageReceiver = messageReceiver;
    }

    NettyImClient(
            NettyClientChannelEventListener listener,
            MessageReceiver messageReceiver,
            NettyClientConfig nettyClientConfig,
            TracerUtil tracerUtil,
            MessageCoder messageCoder) {
        super(messageCoder);
        abstractNettyClient = new AbstractNettyClient(nettyClientConfig, tracerUtil, this, listener, messageCoder);
        this.messageReceiver = messageReceiver;
    }

    NettyImClient(
            NettyClientChannelEventListener listener, MessageReceiver messageReceiver, NettyClientConfig nettyClientConfig, MessageCoder messageCoder) {
        super(messageCoder);

        abstractNettyClient = new AbstractNettyClient(nettyClientConfig, null, this, listener, messageCoder);
        this.messageReceiver = messageReceiver;
    }

    NettyImClient(
            NettyClientChannelEventListener listener, MessageReceiver messageReceiver, TracerUtil tracerUtil, MessageCoder messageCoder) {
        super(messageCoder);
        abstractNettyClient = new AbstractNettyClient(new NettyClientConfig(), tracerUtil, this, listener, messageCoder);
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void close() {
        abstractNettyClient.shutdown();
    }

    @Override
    public Object processRequest(Command command) {
        switch (CommandType.valueOf(command.getHeader().getType())) {
            case PUSH_MESSAGE:
                return messageReceiver.receivePushMessage(command);
            case SEND_MESSAGE:
                messageReceiver.receiveSendMessageAck(command);
                return null;
            default:
                return null;
        }
    }

    @Override
    public void verify(Command msg) {

    }

    @Override
    public Command sendMessage(Command request) {
        return abstractNettyClient.sendSync(request, 1000);
    }

    @Override
    public void sendMessage(Command request, BiConsumer<Command, Command> consumer) {
        try {
            abstractNettyClient.sendAsync(request, 100, consumer);
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
    }

    public NettyImClient start() {
        abstractNettyClient.start();
        abstractNettyClient.createChannel();
        return this;
    }

    public interface MessageReceiver {
        PushMessageAck receivePushMessage(Command command);

        void receiveSendMessageAck(Command command);
    }
}
