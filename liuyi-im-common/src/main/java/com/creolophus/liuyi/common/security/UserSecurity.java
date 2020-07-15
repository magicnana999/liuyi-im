package com.creolophus.liuyi.common.security;

import com.creolophus.liuyi.common.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/7/2 上午12:11
 */
public class UserSecurity {

    private Long userId;
    private String token;
    private Long timestamp;
    private String appKey;
    private List<String> roles = new ArrayList();

    public UserSecurity(){}

    public UserSecurity(User user, String token){
        this.userId = user.getUserId();
        this.timestamp = System.currentTimeMillis();
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
