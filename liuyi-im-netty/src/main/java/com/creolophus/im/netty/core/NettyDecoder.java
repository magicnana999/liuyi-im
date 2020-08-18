package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.Decoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:29
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder {

    private static final int FRAME_MAX_LENGTH = 16777216;

    private Decoder decoder;

    public NettyDecoder(Decoder decoder) {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 0);
        this.decoder = decoder;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if(frame == null) {
            return null;
        }
        ByteBuffer byteBuffer = frame.nioBuffer();
        return decoder.decode(byteBuffer);
    }

    //    @Override
//    public Object decode(ChannelHandlerContext ctx, ByteBuf in)  {
//
//        int readAbles = in.writerIndex();
//        byte[] bytes = new byte[readAbles];
//        in.readRetainedSlice(readAbles).nioBuffer(0, readAbles).get(bytes);
//        Command command =  serializer.decode(bytes);
//        logger.debug("{}", JSON.toJSONString(command) );
//        return command;
//    }
}
