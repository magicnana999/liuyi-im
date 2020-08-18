package com.creolophus.im.netty.serializer;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandDecoder;
import com.creolophus.liuyi.common.json.JSON;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2018/10/16 下午5:18
 */
public class JsonCommandDecoder implements CommandDecoder {

    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        return JSON.parseObject(body, clazz);
    }

    @Override
    public Command decode(final ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();

        byte[] data = new byte[length];
        byteBuffer.get(data);

        Command cmd = JSON.parseObject(data, Command.class);
        return cmd;
    }

    @Override
    public Command decode(final byte[] array) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        return decode(byteBuffer);
    }


}
