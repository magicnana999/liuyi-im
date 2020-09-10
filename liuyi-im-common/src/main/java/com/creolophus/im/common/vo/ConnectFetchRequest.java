package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/9/16 上午10:52
 */
public class ConnectFetchRequest extends AbstractEntity {


    private String sdkVersion;
    private String device;
    private Long currentId;
    private List<ConnectConversationsRequest> conversations = new ArrayList();

    public List<ConnectConversationsRequest> getConversations() {
        return conversations;
    }

    public void setConversations(List<ConnectConversationsRequest> conversations) {
        this.conversations = conversations;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentMessageId) {
        this.currentId = currentMessageId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }
}
