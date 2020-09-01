package com.creolophus.im.sdk;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.type.LoginAck;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.SendMessageMsg;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/8/4 下午5:39
 */
public abstract class AbstractImClient implements LiuyiImClient {
    private static final ReentrantLock lock = new ReentrantLock();
    private Context context;
    private volatile boolean isLogin = false;

    private MessageCoder messageCoder;

    protected AbstractImClient(MessageCoder messageCoder) {
        this.context = new Context();
        this.messageCoder = messageCoder;
    }

    public Command buildAckCommand(Command request) {
        Command response = Command.newAck(request.getHeader().getSeq(), request.getHeader().getType(), "OK").withToken(context.getToken());
        return response;
    }

    public Command buildLoginCommand(String token) {
        LoginMsg client = new LoginMsg();
        client.setDeviceLabel(System.getProperty("os.name"));
        client.setSdkName("liuyi-im-nettysdk");
        client.setSdkVersion("1.0.0");

        Command command = Command.newMsg(CommandType.LOGIN.value(), client).withToken(token);
        return command;
    }

    public Command buildSendMessageCommand(int messageType, String messageBody, long targetId) {
        SendMessageMsg input = new SendMessageMsg();
        input.setMessageBody(messageBody);
        input.setMessageType(messageType);
        input.setTargetId(targetId);
        Command command = Command.newMsg(CommandType.SEND_MESSAGE.value(), input).withToken(context.getToken());
        return command;
    }

    @Override
    public void login(String token) {

        if(lock.tryLock()){
            try{
                if(isLogin){
                    return;
                }
                context.setToken(token);
                Command request = buildLoginCommand(token);
                try {
                    sendMessage(request, (request1, ack) -> {
                        LoginAck loginAck = messageCoder.decode(ack.getBody(), LoginAck.class);
                        context.setAppKey(loginAck.getAppKey());
                        context.setUserId(loginAck.getUserId());
                        isLogin = true;
                    });

                } catch (Throwable e) {
                    throw new RuntimeException("无法登录",e);
                }
            }finally {
                lock.unlock();
            }
        }
    }

    @Override
    public long sendMessage(int messageType, String messageBody, long targetId) {
        Command request = buildSendMessageCommand(messageType, messageBody, targetId);
        try {
            sendMessage(request, (request1, ack) -> {
                System.out.println(JSON.toJSONString(request1));
                System.out.println(JSON.toJSONString(ack));
            });
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
        return 0;
    }

    public abstract Command sendMessage(Command reqeust);

    public abstract void sendMessage(Command request, BiConsumer<Command, Command> consumer);

}
