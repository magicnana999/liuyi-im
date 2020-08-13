package com.creolophus.im.sdk;

import com.creolophus.im.protocol.Command;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:50
 */
public interface LiuyiImClient {

    Command buildAckCommand(Command command);

    void close();

    void login(String token);

    long sendMessage(int messageType, String messageBody, long targetId);
}
