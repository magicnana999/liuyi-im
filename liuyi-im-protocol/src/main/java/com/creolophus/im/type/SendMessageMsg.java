package com.creolophus.im.type;


import com.creolophus.im.internal.MessageBody;
import com.creolophus.liuyi.common.json.GsonUtil;

/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class SendMessageMsg {

    private Integer messageType;
    private Integer messageKind;
    private Long targetId;
    private String messageBody;

    public SendMessageMsg() {
    }

    public SendMessageMsg(Integer messageType, Integer messageKind, Long targetId, String messageBody) {
        this.messageType = messageType;
        this.targetId = targetId;
        this.messageBody = messageBody;
        this.messageKind = messageKind;
    }

    public SendMessageMsg(Integer messageType, Integer messageKind, Long targetId, MessageBody messageBody) {
        this.messageType = messageType;
        this.targetId = targetId;
        this.messageBody = GsonUtil.toJson(messageBody);
        this.messageKind = messageKind;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public <T> T getMessageBodyInternal() {
        return MessageBody.toJava(this.getMessageKind(), this.getMessageBody());
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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody.toJson();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messageType\":").append(messageType);
        sb.append(",\"messageKind\":").append(messageKind);
        sb.append(",\"targetId\":").append(targetId);
        sb.append(",\"messageBody\":\"").append(messageBody).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
