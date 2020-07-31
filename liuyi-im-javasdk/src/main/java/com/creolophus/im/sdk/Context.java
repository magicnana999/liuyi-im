package com.creolophus.im.sdk;

/**
 * @author magicnana
 * @date 2020/7/30 上午11:37
 */
public class Context {

    private String token;
    private String appKey;
    private long userId;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
