package com.creolophus.liuyi.io;

import com.creolophus.liuyi.common.base.AbstractVo;

/**
 * @author magicnana
 * @date 2020/1/19 下午5:36
 */
public class SendMessageOut  extends AbstractVo {

    private Long messageId;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
