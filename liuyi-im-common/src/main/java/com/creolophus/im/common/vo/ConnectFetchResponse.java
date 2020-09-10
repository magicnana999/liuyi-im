package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/9/16 上午10:52
 */
public class ConnectFetchResponse extends AbstractEntity {

    private Long unread;
    private List<ConnectConversationsResponse> conversations = new ArrayList<>();

    public List<ConnectConversationsResponse> getConversations() {
        return conversations;
    }

    public void setConversations(List<ConnectConversationsResponse> conversations) {
        this.conversations = conversations;
    }

    public Long getUnread() {
        return unread;
    }

    public void setUnread(Long unread) {
        this.unread = unread;
    }
}
