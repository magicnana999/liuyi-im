package com.creolophus.im.sdk;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.netty.config.NettyClientConfig;
import com.creolophus.im.netty.core.AbstractNettyClient;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.netty.exception.NettyCommandException;
import com.creolophus.im.netty.exception.NettyCommandWithResException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.Header;
import com.creolophus.im.type.LoginAck;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.SendMessageMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class NettyImClient implements LiuyiImClient, RequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NettyImClient.class);

    private static final ReentrantLock lock = new ReentrantLock();

    private volatile Context context;
    private volatile boolean isLogin = false;

    private AbstractNettyClient abstractNettyClient;


    private MessageReceiver messageReceiver;
    private NettyClientChannelEventListener nettyClientChannelEventListener;
    private MessageCoder messageCoder;


    public NettyImClient(NettyClientChannelEventListener listener, MessageReceiver messageReceiver, MessageCoderSelector messageCoder) {
        this.context = new Context();
        this.messageCoder = messageCoder.getMessageCoder();
        this.messageReceiver = messageReceiver;
        this.nettyClientChannelEventListener = listener;

        abstractNettyClient = new AbstractNettyClient(new NettyClientConfig(), null, this, listener, messageCoder.getMessageCoder());
        this.messageReceiver = messageReceiver;
    }

    private Command buildAckCommand(Command request, Object obj) {
        Command response = Command.newAck(request.getHeader().getSeq(), request.getHeader().getType(), obj).withToken(context.getToken());
        return response;
    }

    private Command buildLoginCommand(String token) {
        LoginMsg client = new LoginMsg();
        client.setDeviceLabel(System.getProperty("os.name"));
        client.setSdkName("liuyi-im-nettysdk");
        client.setSdkVersion("1.0.0");

        Command command = Command.newMsg(CommandType.LOGIN.value(), client).withToken(token);
        return command;
    }

    private Command buildSendMessageCommand(int messageType, String messageBody, long targetId) {
        SendMessageMsg input = new SendMessageMsg();
        input.setMessageBody(messageBody);
        input.setMessageType(messageType);
        input.setTargetId(targetId);
        Command command = Command.newMsg(CommandType.SEND_MESSAGE.value(), input).withToken(context.getToken());
        return command;
    }

    @Override
    public void close() {
        abstractNettyClient.shutdown();
    }

    @Override
    public void login(String token, BiConsumer<Command, Command> consumer) {

        if(lock.tryLock()) {
            try {
                if(isLogin) {
                    return;
                }
                context.setToken(token);
                Command request = buildLoginCommand(token);
                try {
                    sendCommand(request, (request1, ack) -> {
                        LoginAck loginAck = messageCoder.decode(ack.getBody(), LoginAck.class);
                        context.setAppKey(loginAck.getAppKey());
                        context.setUserId(loginAck.getUserId());
                        isLogin = true;
                        ack.setBody(loginAck);
                        consumer.accept(request1, ack);
                    });

                } catch (Throwable e) {
                    throw new RuntimeException("无法登录", e);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public long sendMessage(int messageType, String messageBody, long targetId, BiConsumer<Command, Command> consumer) {
        Command request = buildSendMessageCommand(messageType, messageBody, targetId);
        try {
            sendCommand(request, (request1, ack) -> {
                System.out.println(JSON.toJSONString(request1));
                System.out.println(JSON.toJSONString(ack));
                consumer.accept(request1, ack);
            });
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
        return 0;
    }

    @Override
    public void start() {
        abstractNettyClient.start();
        abstractNettyClient.createChannel();
    }

    private Object processRequestInner(Command command) {
        if(command.getHeader().getCode() == Header.MSG) {
            switch (CommandType.valueOf(command.getHeader().getType())) {
                case PUSH_MESSAGE:
                    return messageReceiver.receivePushMessage(command);
                default:
                    return null;
            }
        }
        return null;
    }

    private void sendCommand(Command request, BiConsumer<Command, Command> consumer) {
        try {
            abstractNettyClient.sendAsync(request, 100, consumer);
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
    }

    @Override
    public void verify(Command msg) {

    }

    @Override
    public Object processRequest(Command cmd) {

        Command response = null;
        try {
            Object obj = processRequestInner(cmd);
            if(obj != null) {
                response = buildAckCommand(cmd, obj);
            }
        } catch (NettyCommandWithResException e) {
            logger.error(e.getMessage(), e);
            response = e.getResponse();
        } catch (NettyCommandException e) {
            logger.error(e.getMessage(), e);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), e.getNettyError());
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), NettyError.E_ERROR);
        }
        return response;
    }
}
