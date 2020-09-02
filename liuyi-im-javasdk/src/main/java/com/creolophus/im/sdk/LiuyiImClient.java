package com.creolophus.im.sdk;

import com.creolophus.im.protocol.Command;

import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:50
 */
public interface LiuyiImClient {

    void close();

    void login(String token, BiConsumer<Command, Command> consumer);

    long sendMessage(int messageType, String messageBody, long targetId, BiConsumer<Command, Command> consumer);

    void start();

}
