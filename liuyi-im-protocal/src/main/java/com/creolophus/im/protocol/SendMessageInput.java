package com.creolophus.im.protocol;


/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class SendMessageInput {

    private Integer messageType;
    private Long targetId;
    private String messageBody;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageType\":").append(messageType);
        sb.append(",\"targetId\":").append(targetId);
        sb.append(",\"messageBody\":\"").append(messageBody).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
