package com.creolophus.im.netty.serializer;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.Decoder;
import com.creolophus.liuyi.common.json.JSON;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2018/10/16 下午5:18
 */
public class JsonDecoder implements Decoder {

    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        return JSON.parseObject(body.toString(), clazz);
    }

    @Override
    public Command decode(ByteBuffer byteBuffer) {
        return JSON.parseObject(byteBuffer.array(),Command.class);
    }
}
