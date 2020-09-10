package com.creolophus.im.coder;

import com.creolophus.im.internal.*;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.type.LoginAck;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.SendMessageMsg;
import org.junit.Assert;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public abstract class CoderTest extends Coder {


    protected void foo() {

        MessageCoder gsonCoder = getMessageCode();

        {
            ByteBuffer byteBuffer = gsonCoder.encode(loginMsg);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), LoginMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), loginMsg.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), loginMsg.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), loginMsg.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), loginMsg.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((LoginMsg) command.getBody()).getDeviceLabel(), ((LoginMsg) loginMsg.getBody()).getDeviceLabel());

        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(loginAck);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), LoginAck.class));
            Assert.assertEquals("不对啊~", command.getToken(), loginAck.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), loginAck.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), loginAck.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), loginAck.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((LoginAck) command.getBody()).getUserId(), ((LoginAck) loginAck.getBody()).getUserId());
        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(sendMessageMsgText);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), sendMessageMsgText.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), sendMessageMsgText.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), sendMessageMsgText.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), sendMessageMsgText.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg) command.getBody()).getMessageBody(), ((SendMessageMsg) sendMessageMsgText.getBody()).getMessageBody());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBodyInternal().getClass(), MessageText.class);
        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(sendMessageMsgUrl);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), sendMessageMsgUrl.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), sendMessageMsgUrl.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), sendMessageMsgUrl.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), sendMessageMsgUrl.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg) command.getBody()).getMessageBody(), ((SendMessageMsg) sendMessageMsgUrl.getBody()).getMessageBody());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBodyInternal().getClass(), MessageUrl.class);
        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(sendMessageMsgImage);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), sendMessageMsgImage.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), sendMessageMsgImage.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), sendMessageMsgImage.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), sendMessageMsgImage.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg) command.getBody()).getMessageBody(), ((SendMessageMsg) sendMessageMsgImage.getBody()).getMessageBody());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBodyInternal().getClass(), MessageImage.class);
        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(sendMessageMsgAudio);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), sendMessageMsgAudio.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), sendMessageMsgAudio.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), sendMessageMsgAudio.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), sendMessageMsgAudio.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg) command.getBody()).getMessageBody(), ((SendMessageMsg) sendMessageMsgAudio.getBody()).getMessageBody());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBodyInternal().getClass(), MessageAudio.class);
        }

        {
            ByteBuffer byteBuffer = gsonCoder.encode(sendMessageMsgVideo);
            Command command = gsonCoder.decode(byteBuffer);
            command.setBody(gsonCoder.decode(command.getBody(), SendMessageMsg.class));
            Assert.assertEquals("不对啊~", command.getToken(), sendMessageMsgVideo.getToken());
            Assert.assertEquals("不对啊~", command.getHeader().getSeq(), sendMessageMsgVideo.getHeader().getSeq());
            Assert.assertEquals("不对啊~", command.getHeader().getType(), sendMessageMsgVideo.getHeader().getType());
            Assert.assertEquals("不对啊~", command.getHeader().getCode(), sendMessageMsgVideo.getHeader().getCode());
            Assert.assertEquals("不对啊~", ((SendMessageMsg) command.getBody()).getMessageBody(), ((SendMessageMsg) sendMessageMsgVideo.getBody()).getMessageBody());
            Assert.assertEquals("不对啊~", ((SendMessageMsg)command.getBody()).getMessageBodyInternal().getClass(), MessageVideo.class);
        }
    }

    protected abstract MessageCoder getMessageCode();


}