package com.creolophus.liuyi.io;

/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class PushMessageOut {

    private Long messageId;
    private Integer messageType;
    private Long groupId;
    private String messageBody;
    private Long receiverId;
    private Long senderId;

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

    public String getMessageBody() {
        return messageBody;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public PushMessageOut(Long messageId, Integer messageType, Long groupId, String messageBody, Long receiverId,Long senderId) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.groupId = groupId;
        this.messageBody = messageBody;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public PushMessageOut(){}
}
