package com.creolophus.im.netty.serializer;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandEncoder;
import com.creolophus.liuyi.common.json.JSON;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2018/10/16 下午5:18
 */
public class JsonCommandEncoder implements CommandEncoder {

    @Override
    public ByteBuffer encode(Command nettyCommand) {

        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] bytes = JSON.toJSONBytes(nettyCommand);
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
