package com.creolophus.tomato.vo;

/**
 * @author magicnana
 * @date 2020/9/8 4:27 PM
 */
public class UserToken {
    private Long userId;
    private Long imId;

    private String token;
    private String imToken;

    public UserToken(Long userId, Long imId, String token, String imToken) {
        this.userId = userId;
        this.imId = imId;
        this.token = token;
        this.imToken = imToken;
    }

    public UserToken() {
    }

    public Long getImId() {
        return imId;
    }

    public void setImId(Long imId) {
        this.imId = imId;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
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
        sb.append("\"userId\":").append(userId);
        sb.append(",\"imId\":").append(imId);
        sb.append(",\"token\":\"").append(token).append('\"');
        sb.append(",\"imToken\":\"").append(imToken).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
