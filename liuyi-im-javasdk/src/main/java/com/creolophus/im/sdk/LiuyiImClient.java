package com.creolophus.im.sdk;

import com.creolophus.im.protocol.Command;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:50
 */
public interface LiuyiImClient {

    boolean isConnected();
    boolean isLogin();
    boolean isConnectedAndLogin();

    void login(String token);
    void close();
    long sendMessage(int messageType,String messageBody,long targetId);
    Command buildLoginCommand(String token);
    Command buildSendMessageCommand(int messageType, String messageBody, long targetId);
    Command buildAckCommand(Command request);



}
