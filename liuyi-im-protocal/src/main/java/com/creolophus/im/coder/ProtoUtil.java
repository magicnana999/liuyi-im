package com.creolophus.im.coder;

import com.creolophus.im.protobuf.*;
import com.creolophus.im.type.*;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author magicnana
 * @date 2020/8/20 2:07 PM
 */
public class ProtoUtil {


    public static <T> T decode(Any body, Class<T> clazz) throws InvalidProtocolBufferException {
        if(clazz.equals(LoginAck.class)) {
            ProtoLoginAck.LoginAck loginAck = body.unpack(ProtoLoginAck.LoginAck.class);
            return (T) toLoginAck(loginAck);
        } else if(clazz.equals(LoginMsg.class)) {
            ProtoLoginMsg.LoginMsg loginMsg = body.unpack(ProtoLoginMsg.LoginMsg.class);
            return (T) toLoginMsg(loginMsg);
        } else if(clazz.equals(SendMessageMsg.class)) {
            ProtoSendMessageMsg.SendMessageMsg sendMessageMsg = body.unpack(ProtoSendMessageMsg.SendMessageMsg.class);
            return (T) toSendMessageMsg(sendMessageMsg);
        } else if(clazz.equals(SendMessageAck.class)) {
            ProtoSendMessageAck.SendMessageAck sendMessageAck = body.unpack(ProtoSendMessageAck.SendMessageAck.class);
            return (T) toSendMessageAck(sendMessageAck);
        } else if(clazz.equals(PushMessageMsg.class)) {
            ProtoPushMessageMsg.PushMessageMsg pushMessageMsg = body.unpack(ProtoPushMessageMsg.PushMessageMsg.class);
            return (T) toPushMessageMsg(pushMessageMsg);
        } else if(clazz.equals(PushMessageAck.class)) {
            ProtoPushMessageAck.PushMessageAck pushMessageAck = body.unpack(ProtoPushMessageAck.PushMessageAck.class);
            return (T) toPushMessageAck(pushMessageAck);
        }
        return null;
    }

    public static LoginAck toLoginAck(ProtoLoginAck.LoginAck loginAck) {
        return new LoginAck(loginAck.getAppKey(), loginAck.getToken(), loginAck.getUserId());
    }

    public static ProtoLoginAck.LoginAck toLoginAck(LoginAck loginAck) {
        return ProtoLoginAck.LoginAck.newBuilder().setUserId(loginAck.getUserId()).setAppKey(loginAck.getAppKey()).setToken(loginAck.getToken()).build();
    }

    public static LoginMsg toLoginMsg(ProtoLoginMsg.LoginMsg loginMsg) {
        return new LoginMsg(loginMsg.getDeviceLabel(), loginMsg.getSdkName(), loginMsg.getSdkVersion());
    }

    public static ProtoLoginMsg.LoginMsg toLoginMsg(LoginMsg loginMsg) {
        return ProtoLoginMsg.LoginMsg.newBuilder()
                .setDeviceLabel(loginMsg.getDeviceLabel())
                .setSdkName(loginMsg.getSdkName())
                .setSdkVersion(loginMsg.getSdkVersion())
                .build();
    }

    public static ProtoPushMessageAck.PushMessageAck toPushMessageAck(PushMessageAck pushMessageAck) {
        return ProtoPushMessageAck.PushMessageAck.newBuilder().setMessageId(pushMessageAck.getMessageId()).build();
    }

    public static PushMessageAck toPushMessageAck(ProtoPushMessageAck.PushMessageAck pushMessageAck) {
        return new PushMessageAck(pushMessageAck.getMessageId(), pushMessageAck.getGroupId(), pushMessageAck.getReceiverId(), pushMessageAck.getSenderId());
    }

    public static ProtoPushMessageMsg.PushMessageMsg toPushMessageMsg(PushMessageMsg pushMessageMsg) {
        return ProtoPushMessageMsg.PushMessageMsg.newBuilder()
                .setGroupId(pushMessageMsg.getGroupId())
                .setMessageId(pushMessageMsg.getMessageId())
                .setMessageType(pushMessageMsg.getMessageType())
                .setReceiverId(pushMessageMsg.getReceiverId())
                .setSenderId(pushMessageMsg.getSenderId())
                .setMessageBody(pushMessageMsg.getMessageBody())
                .build();
    }

    public static PushMessageMsg toPushMessageMsg(ProtoPushMessageMsg.PushMessageMsg pushMessageMsg) {
        return new PushMessageMsg(pushMessageMsg.getMessageId(), pushMessageMsg.getMessageType(), pushMessageMsg.getGroupId(),
                                  pushMessageMsg.getMessageBody(), pushMessageMsg
                .getReceiverId(), pushMessageMsg.getSenderId());
    }

    public static ProtoSendMessageAck.SendMessageAck toSendMessageAck(SendMessageAck sendMessageAck) {
        return ProtoSendMessageAck.SendMessageAck.newBuilder().setMessageId(sendMessageAck.getMessageId()).build();
    }

    public static SendMessageAck toSendMessageAck(ProtoSendMessageAck.SendMessageAck sendMessageAck) {
        return new SendMessageAck(sendMessageAck.getMessageId());
    }

    public static ProtoSendMessageMsg.SendMessageMsg toSendMessageMsg(SendMessageMsg sendMessageMsg) {
        return ProtoSendMessageMsg.SendMessageMsg.newBuilder()
                .setMessageType(sendMessageMsg.getMessageType())
                .setTargetId(sendMessageMsg.getTargetId())
                .setMessageBody(sendMessageMsg.getMessageBody())
                .build();
    }

    public static SendMessageMsg toSendMessageMsg(ProtoSendMessageMsg.SendMessageMsg sendMessageMsg) {
        return new SendMessageMsg(sendMessageMsg.getMessageType(), sendMessageMsg.getTargetId(), sendMessageMsg.getMessageBody());
    }
}
