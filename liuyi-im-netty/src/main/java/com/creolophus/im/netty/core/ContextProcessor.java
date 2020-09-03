package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.domain.Command;
import io.netty.channel.Channel;

import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/7/15 上午11:38
 */
public interface ContextProcessor {

    void clearContext();

    String getAppKey();

    void setAppKey(String appKey);

    Channel getChannel();

    void setChannel(Channel channel);

    Command getRequest();

    void setRequest(Command request);

    Command getResponse();

    void setResponse(Command response);

    Session getSession();

    void setSession(Session session);

    String getToken();

    void setToken(String token);

    long getUserId();

    void setUserId(long userId);

    void initContext(Channel channel, Command command);

    void initContext(Session session, Command command);

    void validateAfterVerify(Command command);

    void validateCommand(Command command);
}
