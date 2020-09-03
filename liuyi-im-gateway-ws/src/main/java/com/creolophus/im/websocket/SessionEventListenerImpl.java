package com.creolophus.im.websocket;

import com.creolophus.im.domain.UserSession;
import com.creolophus.im.netty.core.SessionEventListener;
import com.creolophus.im.protocol.domain.Command;
import com.creolophus.im.service.UserSessionClientService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/7/15 下午4:21
 */
@Component
public class SessionEventListenerImpl implements SessionEventListener {

    @Resource
    private UserSessionClientService userSessionClientService;

    @Override
    public void onFlush(Session session, Command response) {

    }

    @Override
    public void onOpen(Session session) {

    }

    @Override
    public void onClose(Session session) {
        UserSession uc = userSessionClientService.getUserClient(UserSession.getSessionId(session));
        if(uc!=null){
            userSessionClientService.unregisterUserClient(uc);
        }
    }

    @Override
    public void onMessage(Session session, String message) {

    }

    @Override
    public void onError(Session session, Throwable throwable) {

    }
}
