package com.creolophus.im.protocol.coder;

import com.creolophus.im.protocol.domain.Command;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class ProtoCoderTest extends Coder{

    private ProtoCoder protoCoder = new ProtoCoder();

    @Test
    public void decode() {
        {
            ByteBuffer byteBuffer = protoCoder.encode(loginMsg);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),loginMsg.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),loginMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),loginMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),loginMsg.getHeader().getCode());

        }

        {
            ByteBuffer byteBuffer = protoCoder.encode(loginAck);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),"");
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),loginAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),loginAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),loginAck.getHeader().getCode());
        }

        {
            ByteBuffer byteBuffer = protoCoder.encode(sendMessageMsg);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),sendMessageMsg.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),sendMessageMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),sendMessageMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),sendMessageMsg.getHeader().getCode());
        }

        {
            ByteBuffer byteBuffer = protoCoder.encode(sendMessageAck);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),"");
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),sendMessageAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),sendMessageAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),sendMessageAck.getHeader().getCode());
        }

        {
            ByteBuffer byteBuffer = protoCoder.encode(pushMessageMsg);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),"");
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),pushMessageMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),pushMessageMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),pushMessageMsg.getHeader().getCode());

        }

        {
            ByteBuffer byteBuffer = protoCoder.encode(pushMessageAck);
            Command command = protoCoder.decode(byteBuffer);
            Assert.assertEquals("不对啊~",command.getToken(),pushMessageAck.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),pushMessageAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),pushMessageAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),pushMessageAck.getHeader().getCode());
        }
    }


















}