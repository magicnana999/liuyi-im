package com.creolophus.im.service;

import com.creolophus.im.domain.UserChannel;
import com.creolophus.im.domain.UserClient;
import com.creolophus.im.domain.UserSession;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.LoginDown;
import com.creolophus.im.protocol.LoginUp;
import com.creolophus.liuyi.common.json.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2019/1/23 下午5:38
 */
@Component
public class UserSessionClientService extends UserClientService {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionClientService.class);


    private static final ConcurrentHashMap<Long/*userId*/, UserSession> userTable = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String/*channelId*/, UserSession> sessionTable = new ConcurrentHashMap();
    @Resource
    private BackendFeign backendFeign;

    @Resource
    private ContextProcessor contextProcessor;

    private String getAppKey() {
        return contextProcessor.getAppKey();
    }

    private Session getSession() {
        return contextProcessor.getSession();
    }

    private String getToken() {
        return contextProcessor.getToken();
    }

    public UserSession getUserClient(Long userId) {
        return userTable.get(userId);
    }

    public UserSession getUserClient(String sessionId) {
        return sessionTable.get(sessionId);
    }

    private Long getUserId() {
        return contextProcessor.getUserId();
    }

    private void insertSessionTable(UserSession client) {
        sessionTable.put(client.getSessionId(), client);
    }

    private void insertUserTable(UserSession client) {
        UserSession origin = userTable.putIfAbsent(client.getUserId(), client);
        if(origin != null && origin.getSession() != null) {
            try {
                origin.getSession().close();
                logger.debug("channel has been closed {}", origin.getSessionId());
            } catch (IOException e) {
                throw new RuntimeException("此用户已存在 UserSession,并且无法关闭" + client.getUserId() + " " + client.getSessionId());
            }
        }
    }

    @Override
    public LoginDown login(LoginUp input) {
        UserSession client = new UserSession();
        client.setSession(getSession());
        client.setUserId(getUserId());
        client.setAppKey(getAppKey());
        client.setSocketType(UserChannel.SocketType.SOCKET.getValue());
        registerUserClient(client);
        backendFeign.registerUserClient("127.0.0.1", 33008, client.getUserId());

        LoginDown ret = new LoginDown();
        ret.setAppKey(getAppKey());
        ret.setToken(getToken());
        ret.setUserId(getUserId());
        logger.info("用户登录 成功 {}.{}", ret.getAppKey(), ret.getUserId());
        return ret;
    }

    @Override
    public void pushMessage(Long receiverId, Command response) {
        if(response != null) {
            try {
                UserSession uc = getUserClient(receiverId);
                if(uc != null && uc.getSession() != null) {
                    uc.getSession().getBasicRemote().sendText(JSON.toJSONString(response));
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Nothing to response");
        }
    }

    public void registerUserClient(UserClient userClient) {
        UserSession client = (UserSession) userClient;
        insertUserTable(client);
        insertSessionTable(client);
    }

    private void removeSessionTable(UserSession client) {
        sessionTable.remove(client.getSessionId());
    }

    private void removeUserTable(UserSession client) {
        userTable.remove(client.getUserId());
    }

    public void unregisterUserClient(UserSession client) {
        backendFeign.unregisterUserClient("127.0.0.1", 33008, client.getUserId());
        removeUserTable(client);
        removeSessionTable(client);
        logger.info("用户断开 完成 {}.{}", client.getAppKey(), client.getUserId());
    }

}
