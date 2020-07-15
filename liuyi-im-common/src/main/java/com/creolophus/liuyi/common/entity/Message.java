package com.creolophus.liuyi.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import org.beetl.sql.core.annotatoin.AssignID;
import org.springframework.beans.BeanUtils;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-02-19
 */

public class Message extends AbstractEntity {

    @AssignID
    private Long messageId;
    @AssignID
    private Long receiverId;
    private Long groupId;
    private Integer messageType;
    private String messageBody;
    private Date createTime;
    private Date sendTime;
    private Long senderId;

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

    public Integer getMessageType() {
        return messageType;
    }
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
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

    public Long getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public enum MessageType {
        SINGLE(1),
        GROUP(2);

        int value;

        MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    }
}
