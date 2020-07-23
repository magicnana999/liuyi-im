package com.creolophus.im.netty.core;

import com.creolophus.im.netty.protocol.Command;
import io.netty.channel.Channel;

import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/7/15 上午11:38
 */
public interface ContextProcessor {

    void initContext(Channel channel, Command command);
    void initContext(Session session, Command command);
    void validateCommand(Command command);
    void validateUserId(Command command);

    void clearContext();

    String getAppKey();

    void setAppKey(String appKey);

    String getToken();

    void setToken(String token);

    long getUserId();

    void setUserId(long userId);

    Channel getChannel();

    void setChannel(Channel channel);

    Session getSession();

    void setSession(Session session);

    Command getRequest();

    void setRequest(Command request);

    Command getResponse();

    void setResponse(Command response);
}
