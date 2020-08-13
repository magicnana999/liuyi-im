package com.creolophus.im.sdk;

import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.AbstractNettyClient;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.PushMessageUp;

import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class NettyImClient extends AbstractImClient implements LiuyiImClient, RequestProcessor {

    private AbstractNettyClient abstractNettyClient;
    private MessageReceiver messageReceiver;

    NettyImClient(NettyClientChannelEventListener listener, MessageReceiver messageReceiver) {
        abstractNettyClient = new AbstractNettyClient(new NettyClientConfig(), null, this, listener);
        this.messageReceiver = messageReceiver;
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

    @Override
    public Command sendMessage(Command request) {
        return abstractNettyClient.sendSync(request, 1000);
    }

    public NettyImClient start() {
        abstractNettyClient.start();
        abstractNettyClient.createChannel();
        return this;
    }

    @Override
    public void verify(Command msg) {

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

    public interface MessageReceiver {
        PushMessageUp receivePushMessage(Command command);

        void receiveSendMessageAck(Command command);
    }
}
