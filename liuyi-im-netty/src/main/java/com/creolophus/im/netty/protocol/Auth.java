package com.creolophus.im.netty.protocol;

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


}
