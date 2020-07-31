package com.creolophus.im.netty.core;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.netty.serializer.FastJSONSerializer;
import com.creolophus.im.protocol.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:29
 */
public class NettyDecoder extends FixedLengthFrameDecoder {
    private static final Logger logger = LoggerFactory.getLogger(NettyDecoder.class);

    private FastJSONSerializer serializer = new FastJSONSerializer();
    private int maxFrameLength;

    public NettyDecoder() {
        super(128 * 1024);
        this.serializer = new FastJSONSerializer();
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in)  {

        int readAbles = in.writerIndex();
        byte[] bytes = new byte[readAbles];
        in.readRetainedSlice(readAbles).nioBuffer(0, readAbles).get(bytes);
        Command command =  serializer.decode(bytes);
        logger.debug("{}", JSON.toJSONString(command) );
        return command;
    }
}
