package com.creolophus.im.service;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.domain.UserChannel;
import com.creolophus.im.domain.UserClient;
import com.creolophus.im.domain.UserSession;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.protocol.*;
import com.creolophus.im.processor.UserClientProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2019/1/23 下午5:38
 */
@Component
public class UserSessionHolder extends SessionBaseService implements UserClientProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionHolder.class);


    private static final ConcurrentHashMap<Long/*userId*/, UserSession> userTable = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String/*channelId*/, UserSession> sessionTable = new ConcurrentHashMap();

    public UserSession getUserClient(Long userId) {
        return userTable.get(userId);
    }

    public UserSession getUserClient(String sessionId){
        return sessionTable.get(sessionId);
    }

    private void insertSessionTable(UserSession client) {
        sessionTable.put(client.getSessionId(), client);
    }

    private void insertUserTable(UserSession client) {
        UserSession origin = userTable.putIfAbsent(client.getUserId(), client);
        if(origin != null && origin.getSession()!=null) {
            try {
                origin.getSession().close();
                logger.debug("channel has been closed {}", origin.getSessionId());
            } catch (IOException e) {
                throw new RuntimeException("此用户已存在 UserSession,并且无法关闭"+ client.getUserId() +" "+client.getSessionId());
            }
        }
    }

    @Override
    public void registerUserClient(UserClient userClient) {
        UserSession client = (UserSession)userClient;
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
    }

    @Resource
    private BackendFeign backendFeign;

    @Override
    public LoginDown login(LoginUp input) {
        UserSession client = new UserSession();
        client.setSession(getSession());
        client.setUserId(getUserId());
        client.setAppKey(getAppKey());
        client.setSocketType(UserChannel.SocketType.SOCKET.getValue());
        registerUserClient(client);
        backendFeign.registerUserClient("127.0.0.1",33008,client.getUserId());

        LoginDown ret = new LoginDown();
        ret.setAppKey(getAppKey());
        ret.setToken(getToken());
        ret.setUserId(getUserId());
        return ret;
    }

    @Override
    public PushMessageDown pushMessage(PushMessageDown pushMessageDown){
        Command response = Command.newRequest(CommandType.PUSH_MESSAGE.getValue(), pushMessageDown);

        pushMessage(pushMessageDown.getReceiverId(), response);
        return pushMessageDown;
    }

    @Override
    public void pushMessageAck(PushMessageUp pushMessageUp) {
        logger.debug("消息推送 到达 {} -> {}.{}", pushMessageUp.getSenderId(),pushMessageUp.getReceiverId(), pushMessageUp.getMessageId());
    }


    public void pushMessage(Long receiverId, Command response){
        if(response != null) {
            try {
                UserSession uc = getUserClient(receiverId);
                if(uc!=null && uc.getSession()!=null){
                    uc.getSession().getBasicRemote().sendText(JSON.toJSONString(response));
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Nothing to response");
        }
    }

}
