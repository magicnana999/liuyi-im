package com.creolophus.liuyi.netty.core;

import com.creolophus.liuyi.netty.protocol.Command;
import com.creolophus.liuyi.netty.serializer.FastJSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:28
 */
public class NettyEncoder extends MessageToByteEncoder<Command> {


    private FastJSONSerializer serializer = new FastJSONSerializer();

    @Override
    public void encode(ChannelHandlerContext ctx, Command nettyCommand, ByteBuf out) {
        out.writeBytes(serializer.encode(nettyCommand));
    }
}
