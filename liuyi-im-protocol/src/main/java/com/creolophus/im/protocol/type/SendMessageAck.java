package com.creolophus.im.protocol.type;


/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class SendMessageAck {

    private Long messageId;

    public SendMessageAck(Long messageId) {
        this.messageId = messageId;
    }

    public SendMessageAck() {}

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageId\":").append(messageId);
        sb.append('}');
        return sb.toString();
    }
}
