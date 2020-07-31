package com.creolophus.im.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creolophus.im.protocol.*;

import javax.annotation.processing.Messager;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class DefaultImClientImpl extends SocketClient implements LiuyiImClient{

    private Context context;
    private volatile boolean isLogin = false;

    private static final ReentrantLock lock = new ReentrantLock();

    DefaultImClientImpl(Configration configration,PushProcessor pushProcessor) {
        super(configration,pushProcessor);
        this.context = new Context();
    }


    @Override
    public boolean isConnected() {
        return super.isConnect();
    }

    @Override
    public boolean isLogin() {
        return isLogin;
    }

    @Override
    public boolean isConnectedAndLogin() {
        return super.isConnect() && isLogin();
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
                    this.sendMessage(request, (request1, ack) -> {
                        LoginOutput ok = ((JSONObject)ack.getBody()).toJavaObject(LoginOutput.class);
                        if(ok!=null){
                            context.setAppKey(ok.getAppKey());
                            context.setUserId(ok.getUserId());
                        }
                    });
                    isLogin=true;
                } catch (InterruptedException e) {
                    throw new RuntimeException("无法登录",e);
                } catch (ClosedChannelException e) {
                    throw new RuntimeException("无法登录",e);
                }
            }finally {
                lock.unlock();
            }
        }


    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long sendMessage(int messageType, String messageBody, long targetId) {
        Command request = buildSendMessageCommand(messageType,messageBody,targetId);
        try {
            this.sendMessage(request, (request1, ack) -> {
                System.out.println(JSON.toJSONString(request1));
                System.out.println(JSON.toJSONString(ack));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException("无法发送消息",e);
        } catch (ClosedChannelException e) {
            throw new RuntimeException("无法发送消息",e);
        }
        return 0;
    }


    @Override
    public Command buildLoginCommand(String token){
        LoginInput client = new LoginInput();
        client.setDeviceLabel(System.getProperty("os.name"));
        client.setSdkName("liuyi-im-sdk");
        client.setSdkVersion("1.0.0");

        Command command = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(token);
        return command;
    }

    @Override
    public Command buildSendMessageCommand(int messageType, String messageBody, long targetId){
        SendMessageInput input = new SendMessageInput();
        input.setMessageBody(messageBody);
        input.setMessageType(messageType);
        input.setTargetId(targetId);
        Command command = Command.newRequest(CommandType.SEND_MESSAGE.getValue(),input).withToken(context.getToken());
        return command;
    }

    @Override
    public Command buildAckCommand(Command request) {
        Command response = Command.newResponse(request.getHeader().getSeq(), request.getHeader().getType(), "OK").withToken(context.getToken());
        return response;
    }


}
