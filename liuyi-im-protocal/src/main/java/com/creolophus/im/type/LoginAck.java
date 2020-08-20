package com.creolophus.im.type;

/**
 * server -> client
 *
 * @author magicnana
 * @date 2020-01-11
 */

public class LoginAck {

    private String appKey;
    private String token;
    private Long userId;

    public LoginAck() {
    }

    public LoginAck(String appKey, String token, Long userId) {
        this.appKey = appKey;
        this.token = token;
        this.userId = userId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        sb.append(",\"token\":\"").append(token).append('\"');
        sb.append(",\"userId\":").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
