package com.creolophus.im.sdk;

import com.creolophus.im.protocol.Command;

import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 上午10:51
 */
public class SocketImClient extends AbstractImClient implements LiuyiImClient {

    private SocketClient socketClient;

    SocketImClient(Configration configration, PushProcessor pushProcessor) {
        socketClient = new SocketClient(configration, pushProcessor);
    }

    @Override
    public void close() {
        try {
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Command request, BiConsumer<Command, Command> consumer) {
        try {
            socketClient.sendMessage(request, consumer);
        } catch (Throwable e) {
            throw new RuntimeException("无法发送消息", e);
        }
    }
}
