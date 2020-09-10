package com.creolophus.im.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-02-19
 */

public class Message extends AbstractEntity {

    private static final long serialVersionUID = -4062436715975427781L;

    @AssignID
    private Long messageId;
    @AssignID
    private Long receiverId;
    private Long groupId;
    private Integer messageType;
    private Integer messageKind;
    private String messageBody;
    private Date createTime;
    private Date sendTime;
    private Long senderId;
    private String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
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
        sb.append(",\"receiverId\":").append(receiverId);
        sb.append(",\"groupId\":").append(groupId);
        sb.append(",\"messageType\":").append(messageType);
        sb.append(",\"messageKind\":").append(messageKind);
        sb.append(",\"messageBody\":\"").append(messageBody).append('\"');
        sb.append(",\"createTime\":\"").append(createTime).append('\"');
        sb.append(",\"sendTime\":\"").append(sendTime).append('\"');
        sb.append(",\"senderId\":").append(senderId);
        sb.append(",\"appKey\":\"").append(appKey).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
