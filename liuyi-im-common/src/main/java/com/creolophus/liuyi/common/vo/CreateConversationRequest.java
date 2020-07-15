package com.creolophus.liuyi.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/10/31 下午12:03
 */
public class CreateConversationRequest extends AbstractEntity {

    private Long targetUserId;
    private String conversationName;

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
