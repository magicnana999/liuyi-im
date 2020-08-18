package com.creolophus.im.sdk;

import com.creolophus.im.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/8/4 下午5:39
 */
public abstract class AbstractImClient implements LiuyiImClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractImClient.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private Context context;
    private volatile boolean isLogin = false;


    private Decoder decoder;
    private Encoder encoder;

    protected AbstractImClient(Decoder decoder, Encoder encoder) {
        this.context = new Context();
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override
    public Command buildAckCommand(Command request) {
        Command response = Command.newResponse(request.getHeader().getSeq(), request.getHeader().getType(), "OK").withToken(context.getToken());
        return response;
    }

    @Override
    public void login(String token) {

        if(lock.tryLock()) {
            try {
                if(isLogin) {
                    return;
                }
                context.setToken(token);
                Command request = buildLoginCommand(token);
                try {
                    sendMessage(request, (request1, ack) -> {
                        LoginDown loginDown = decoder.decode(ack.getBody(), LoginDown.class);
                        context.setAppKey(loginDown.getAppKey());
                        context.setUserId(loginDown.getUserId());

                        logger.debug("用户登录 成功 {}.{}", context.getAppKey(), context.getUserId());
                        isLogin = true;
                    });
//                    Command response = sendMessage(request);
//                    LoginDown loginDown = ((JSONObject) response.getBody()).toJavaObject(LoginDown.class);
//                    context.setAppKey(loginDown.getAppKey());
//                    context.setUserId(loginDown.getUserId());
                } catch (Throwable e) {
                    throw new RuntimeException("无法登录", e);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public long sendMessage(int messageType, String messageBody, long targetId) {
        Command request = buildSendMessageCommand(messageType, messageBody, targetId);
        try {
            sendMessage(request, (request1, ack) -> {
                logger.debug("消息发送 成功 {}.{}", ack.getHeader().getSeq(), ack.getHeader().getCode());
            });
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
        return 0;
    }

    public Command buildLoginCommand(String token) {
        LoginUp client = new LoginUp();
        client.setDeviceLabel(System.getProperty("os.name"));
        client.setSdkName("liuyi-im-nettysdk");
        client.setSdkVersion("1.0.0");

        Command command = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(token);
        return command;
    }

    public Command buildSendMessageCommand(int messageType, String messageBody, long targetId) {
        SendMessageUp input = new SendMessageUp();
        input.setMessageBody(messageBody);
        input.setMessageType(messageType);
        input.setTargetId(targetId);
        Command command = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), input).withToken(context.getToken());
        return command;
    }

    public abstract Command sendMessage(Command request);

    public abstract void sendMessage(Command request, BiConsumer<Command, Command> consumer);

}
