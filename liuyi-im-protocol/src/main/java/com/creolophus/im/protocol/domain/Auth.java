package com.creolophus.im.protocol.domain;

/**
 * @author magicnana
 * @date 2020/1/10 上午11:46
 */
public class Auth {

    private String appKey;
    private Long userId;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"appKey\":\"").append(appKey).append('\"');
        sb.append(",\"userId\":").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
