package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/10/31 下午12:03
 */
public class ConnectConversationsRequest extends AbstractEntity {

    private String conversationId;
    private Long currentId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }
}
