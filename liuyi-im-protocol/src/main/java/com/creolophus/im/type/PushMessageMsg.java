package com.creolophus.im.type;

import com.creolophus.im.internal.*;
import com.creolophus.im.protocol.MessageKind;
import com.creolophus.liuyi.common.json.GsonUtil;

/**
 * Server -> Client
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class PushMessageMsg {

    private Long messageId;
    private Integer messageType;
    private Integer messageKind;
    private Long groupId;
    private String messageBody;
    private Long receiverId;
    private Long senderId;

    public PushMessageMsg(Long messageId, Integer messageType, Integer messageKind, Long groupId, String messageBody, Long receiverId, Long senderId) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.groupId = groupId;
        this.messageBody = messageBody;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.messageKind = messageKind;
    }

    public PushMessageMsg(Long messageId, Integer messageType, Integer messageKind, Long groupId, MessageBody messageBody, Long receiverId, Long senderId) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.groupId = groupId;
        this.messageBody = GsonUtil.toJson(messageBody);
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.messageKind = messageKind;
    }

    public PushMessageMsg() {}

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = GsonUtil.toJson(messageBody);
    }

    public <T> T getMessageBodyInternal() {
        switch (MessageKind.valueOf(this.messageKind)) {
            case TEXT:
                return GsonUtil.toJava(this.getMessageBody(), MessageText.class);
            case URL:
                return GsonUtil.toJava(this.getMessageBody(), MessageUrl.class);
            case IMAGE:
                return GsonUtil.toJava(this.getMessageBody(), MessageImage.class);
            case AUDIO:
                return GsonUtil.toJava(this.getMessageBody(), MessageAudio.class);
            case VIDEO:
                return GsonUtil.toJava(this.getMessageBody(), MessageVideo.class);
            default:
                return null;
        }
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageId\":").append(messageId);
        sb.append(",\"messageType\":").append(messageType);
        sb.append(",\"messageKind\":").append(messageKind);
        sb.append(",\"groupId\":").append(groupId);
        sb.append(",\"messageBody\":\"").append(messageBody).append('\"');
        sb.append(",\"receiverId\":").append(receiverId);
        sb.append(",\"senderId\":").append(senderId);
        sb.append('}');
        return sb.toString();
    }
}
