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

    public static <T> T decode(Any body) throws InvalidProtocolBufferException {
        if(body.is(ProtoLoginAck.LoginAck.class)) {
            ProtoLoginAck.LoginAck loginAck = body.unpack(ProtoLoginAck.LoginAck.class);
            return (T) toLoginAck(loginAck);
        } else if(body.is(ProtoLoginMsg.LoginMsg.class)) {
            ProtoLoginMsg.LoginMsg loginMsg = body.unpack(ProtoLoginMsg.LoginMsg.class);
            return (T) toLoginMsg(loginMsg);
        } else if(body.is(ProtoSendMessageMsg.SendMessageMsg.class)) {
            ProtoSendMessageMsg.SendMessageMsg sendMessageMsg = body.unpack(ProtoSendMessageMsg.SendMessageMsg.class);
            return (T) toSendMessageMsg(sendMessageMsg);
        } else if(body.is(ProtoSendMessageAck.SendMessageAck.class)) {
            ProtoSendMessageAck.SendMessageAck sendMessageAck = body.unpack(ProtoSendMessageAck.SendMessageAck.class);
            return (T) toSendMessageAck(sendMessageAck);
        } else if(body.is(ProtoPushMessageMsg.PushMessageMsg.class)) {
            ProtoPushMessageMsg.PushMessageMsg pushMessageMsg = body.unpack(ProtoPushMessageMsg.PushMessageMsg.class);
            return (T) toPushMessageMsg(pushMessageMsg);
        } else if(body.is(ProtoPushMessageAck.PushMessageAck.class)) {
            ProtoPushMessageAck.PushMessageAck pushMessageAck = body.unpack(ProtoPushMessageAck.PushMessageAck.class);
            return (T) toPushMessageAck(pushMessageAck);
        } else {
            throw new RuntimeException("还没写呢这个类型 " + body.getTypeUrl());
        }
    }

    public static <T> T encode(Object obj) {
        if(obj instanceof LoginAck) {
            return (T) toLoginAck((LoginAck) obj);
        } else if(obj instanceof LoginMsg) {
            return (T) toLoginMsg((LoginMsg) obj);
        } else if(obj instanceof SendMessageMsg) {
            return (T) toSendMessageMsg((SendMessageMsg) obj);
        } else if(obj instanceof SendMessageAck) {
            return (T) toSendMessageAck((SendMessageAck) obj);
        } else if(obj instanceof PushMessageMsg) {
            return (T) toPushMessageMsg((PushMessageMsg) obj);
        } else if(obj instanceof PushMessageAck) {
            return (T) toPushMessageAck((PushMessageAck) obj);
        } else {
            throw new RuntimeException("还没写呢这个类型 " + obj.getClass().getSimpleName());
        }
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
        ProtoPushMessageMsg.PushMessageMsg.Builder builder = ProtoPushMessageMsg.PushMessageMsg.newBuilder();
        if(pushMessageMsg.getGroupId() != null) {
            builder.setGroupId(pushMessageMsg.getGroupId());

        }
        builder.setMessageId(pushMessageMsg.getMessageId());
        builder.setMessageType(pushMessageMsg.getMessageType());
        builder.setReceiverId(pushMessageMsg.getReceiverId());
        builder.setSenderId(pushMessageMsg.getSenderId());
        builder.setMessageBody(pushMessageMsg.getMessageBody());
        return builder.build();
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
