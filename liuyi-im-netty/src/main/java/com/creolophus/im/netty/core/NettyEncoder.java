package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.Command;
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


    @Override
    public void encode(ChannelHandlerContext ctx, Command nettyCommand, ByteBuf out) {
        out.writeBytes(nettyCommand.encode());
    }
}
