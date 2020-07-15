package com.creolophus.liuyi.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/10/31 下午12:03
 */
public class ConnectConversationsResponse extends AbstractEntity {
    private String conversationId;
    private String conversationName;
    private Long unread;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public Long getUnread() {
        return unread;
    }

    public void setUnread(Long unread) {
        this.unread = unread;
    }
}
