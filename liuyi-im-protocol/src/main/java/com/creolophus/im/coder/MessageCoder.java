package com.creolophus.im.coder;

import com.creolophus.im.protocol.Command;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/18 6:09 PM
 */
public interface MessageCoder {

    /**
     * 把 Command.body 反序列化为指定类型
     * @param body
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T decode(Object body, Class<T> clazz);

    /**
     * 从 Netty.LengthFieldBasedFrameDecoder 中解析 Command
     * @param byteBuffer
     * @return
     */
    Command decode(ByteBuffer byteBuffer);

    /**
     * 把 Command 解析为 ByteBuffer 发送到 Netty.LengthFieldBasedFrameDecoder
     * @param command
     * @return
     */
    ByteBuffer encode(Command command);

    String toString(Object command);


}
