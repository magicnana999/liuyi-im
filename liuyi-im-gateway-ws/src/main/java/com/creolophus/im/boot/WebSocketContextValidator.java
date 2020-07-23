package com.creolophus.im.boot;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.netty.core.AbstractContextProcessor;
import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.GlobalSetting;
import com.creolophus.im.common.api.LiuYiApiContextValidator;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.netty.exception.NettyCommandWithResException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.protocol.Command;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/7/15 上午11:23
 */

public class WebSocketContextValidator extends AbstractContextProcessor implements ContextProcessor {

    private static final String SESSION = "SESSION";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

    private static final Logger logger = LoggerFactory.getLogger(WebSocketContextValidator.class);


    @Resource
    private GlobalSetting globalSetting;

    @Override
    public void initContext(Channel channel, Command command) {

    }

    @Override
    public void initContext(Session session, Command command) {
        setSession(session);
        setRequest(command);

        validateCommand(command);
        MdcUtil.setUri("" + command.getHeader().getType());

        if(globalSetting!=null && globalSetting.isDebug()) {
            if(logger.isDebugEnabled()) {
                logger.debug(JSON.toJSONString(command));
            }
        }
    }


    @Override
    public void clearContext() {
        MdcUtil.clearAll();
        super.cleanContext();
    }

    @Override
    public String getAppKey() {
        return getAppKeyByContext();
    }

    @Override
    public void setAppKey(String appKey) {
        setAppKeyByContext(appKey);
    }

    @Override
    public String getToken() {
        return ApiContext.getContext().getToken();
    }

    @Override
    public void setToken(String token) {
        ApiContext.getContext().setToken(token);
    }

    @Override
    public long getUserId() {
        return ApiContext.getContext().getUserId();
    }

    @Override
    public void setUserId(long userId) {
        ApiContext.getContext().setUserId(userId);
    }

    @Override
    public Channel getChannel() {
        return null;
    }

    @Override
    public void setChannel(Channel channel) {

    }

    @Override
    public Session getSession() {
        return ApiContext.getContext().getExt(SESSION);
    }

    @Override
    public void setSession(Session session) {
        ApiContext.getContext().setExt(SESSION, session);
    }

    @Override
    public Command getRequest() {
        return ApiContext.getContext().getExt(REQUEST);
    }

    @Override
    public void setRequest(Command reqCommand) {
        ApiContext.getContext().setExt(REQUEST, reqCommand);
    }

    @Override
    public Command getResponse() {
        return ApiContext.getContext().getExt(RESPONSE);
    }

    @Override
    public void setResponse(Command resCommand) {
        ApiContext.getContext().setExt(RESPONSE, resCommand);
    }
}

