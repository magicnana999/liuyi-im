package com.creolophus.im.coder;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.type.*;
import com.creolophus.liuyi.common.json.GsonUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class JacksonCoderTest extends Coder{

    private JacksonCoder jacksonCoder = new JacksonCoder();

    @Test
    public void decode() {
        {
            ByteBuffer byteBuffer = jacksonCoder.encode(loginMsg);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), LoginMsg.class));
            Assert.assertEquals("不对啊~",command.getToken(),loginMsg.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),loginMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),loginMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),loginMsg.getHeader().getCode());
            Assert.assertEquals("不对啊~",((LoginMsg)command.getBody()).getDeviceLabel(),((LoginMsg)loginMsg.getBody()).getDeviceLabel());

        }

        {
            ByteBuffer byteBuffer = jacksonCoder.encode(loginAck);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), LoginAck.class));
            Assert.assertEquals("不对啊~",command.getToken(),loginAck.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),loginAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),loginAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),loginAck.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((LoginAck)command.getBody()).getUserId(), ((LoginAck)loginAck.getBody()).getUserId());
        }

        {
            ByteBuffer byteBuffer = jacksonCoder.encode(sendMessageMsg);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~",command.getToken(),sendMessageMsg.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),sendMessageMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),sendMessageMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),sendMessageMsg.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBody(), ((SendMessageMsg)sendMessageMsg.getBody()).getMessageBody());
        }

        {
            ByteBuffer byteBuffer = jacksonCoder.encode(sendMessageAck);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), SendMessageAck.class));
            Assert.assertEquals("不对啊~",command.getToken(),sendMessageAck.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),sendMessageAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),sendMessageAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),sendMessageAck.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageAck)command.getBody()).getMessageId(), ((SendMessageAck)sendMessageAck.getBody()).getMessageId());
        }

        {
            ByteBuffer byteBuffer = jacksonCoder.encode(pushMessageMsg);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), PushMessageMsg.class));
            Assert.assertEquals("不对啊~",command.getToken(),pushMessageMsg.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),pushMessageMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),pushMessageMsg.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),pushMessageMsg.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((PushMessageMsg)command.getBody()).getMessageBody(), ((PushMessageMsg)pushMessageMsg.getBody()).getMessageBody());

            System.out.println(GsonUtil.toJson(pushMessageMsg));
            System.out.println(GsonUtil.toJson(command));
        }

        {
            ByteBuffer byteBuffer = jacksonCoder.encode(pushMessageAck);
            Command command = jacksonCoder.decode(byteBuffer);
            command.setBody(jacksonCoder.decode(command.getBody(), PushMessageAck.class));
            Assert.assertEquals("不对啊~",command.getToken(),pushMessageAck.getToken());
            Assert.assertEquals("不对啊~",command.getHeader().getSeq(),pushMessageAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~",command.getHeader().getType(),pushMessageAck.getHeader().getType());
            Assert.assertEquals("不对啊~",command.getHeader().getCode(),pushMessageAck.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((PushMessageAck)command.getBody()).getMessageId(), ((PushMessageAck)pushMessageAck.getBody()).getMessageId());
            System.out.println(GsonUtil.toJson(pushMessageAck));
            System.out.println(GsonUtil.toJson(command));
        }
    }


















}