package com.creolophus.liuyi.io;

import com.creolophus.liuyi.common.base.AbstractVo;
import com.creolophus.liuyi.common.config.LiuyiSetting;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class SendMessageInput extends AbstractVo {

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
}
