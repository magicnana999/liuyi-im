package com.creolophus.im.protocol.type;

/**
 * Server -> Client
 *
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class PushMessageMsg {

    private Long messageId;
    private Integer messageType;
    private Long groupId;
    private String messageBody;
    private Long receiverId;
    private Long senderId;

    public PushMessageMsg(Long messageId, Integer messageType, Long groupId, String messageBody, Long receiverId, Long senderId) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.groupId = groupId;
        this.messageBody = messageBody;
        this.receiverId = receiverId;
        this.senderId = senderId;
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

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageId\":").append(messageId);
        sb.append(",\"messageType\":").append(messageType);
        sb.append(",\"groupId\":").append(groupId);
        sb.append(",\"messageBody\":\"").append(messageBody).append('\"');
        sb.append(",\"receiverId\":").append(receiverId);
        sb.append(",\"senderId\":").append(senderId);
        sb.append('}');
        return sb.toString();
    }
}
