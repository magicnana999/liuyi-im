package com.creolophus.im.coder;

import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.json.GsonUtil;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/18 6:06 PM
 */
public class GsonCoder implements MessageCoder {
    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        return GsonUtil.toJava(body, clazz);
    }

    @Override
    public Command decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();
        byte[] data = new byte[length];
        byteBuffer.get(data);
        return GsonUtil.toJava(data, Command.class);
    }

    @Override
    public ByteBuffer encode(Command command) {
        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] bytes = GsonUtil.toByteArray(command);
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
