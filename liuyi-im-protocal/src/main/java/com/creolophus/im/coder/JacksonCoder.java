package com.creolophus.im.coder;

import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.json.JacksonUtil;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/18 5:37 PM
 */
public class JacksonCoder  implements MessageCoder{
    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        return JacksonUtil.toJava(body, clazz);
    }

    @Override
    public Command decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();
        byte[] data = new byte[length];
        byteBuffer.get(data);
        return JacksonUtil.toJava(data, Command.class);
    }

    @Override
    public ByteBuffer encode(Command command) {
        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] bytes = JacksonUtil.toByteArray(command);
        length += bytes.length;

        ByteBuffer result = ByteBuffer.allocate(length);

        // length
        result.putInt(bytes.length);

        // header data
        result.put(bytes);
        result.flip();

        return result;
    }
}
