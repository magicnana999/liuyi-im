package com.creolophus.im.domain;

import com.creolophus.im.netty.utils.SessionUtil;

import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/1/11 下午2:45
 */
public class UserSession extends UserClient {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static String getSessionId(Session session) {
        return SessionUtil.getSessionId(session);
    }

    public String getSessionId() {
        return getSessionId(getSession());
    }

    @Override
    public String toString() {
        return "{\"userId\":" + getUserId() + ",\"appKey\":" + getAppKey() + ",\"session\":" + getSessionId() + "}";
    }
}
