package com.creolophus.im.netty.serializer;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.Encoder;
import com.creolophus.liuyi.common.json.JSON;

/**
 * @author magicnana
 * @date 2018/10/16 下午5:18
 */
public class JsonEncoder implements Encoder {

    @Override
    public byte[] encode(Command nettyCommand) {
        return JSON.toJSONBytes(nettyCommand);
    }


}
