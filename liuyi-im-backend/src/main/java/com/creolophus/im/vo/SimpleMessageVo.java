package com.creolophus.im.vo;

import com.creolophus.liuyi.common.base.AbstractVo;

import java.util.Date;

/**
 * 消息 Vo
 *
 * @author magicnana
 * @date 2020/2/3 下午4:26
 */
public class SimpleMessageVo extends AbstractVo {
    private Integer messageType;
    private String messageBody;
    private Date sendTime;

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
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
}
