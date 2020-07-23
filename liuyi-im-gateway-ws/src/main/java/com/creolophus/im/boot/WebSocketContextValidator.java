package com.creolophus.im.boot;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.GlobalSetting;
import com.creolophus.im.common.api.LiuYiApiContextValidator;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.netty.exception.NettyCommandWithResException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.netty.protocol.Command;
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

public class WebSocketContextValidator extends LiuYiApiContextValidator implements ContextProcessor {

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
        if(command == null) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_NULL);
        }

        if(command.isEmpty()) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_NULL);
        }


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


    public void throwRemotingCommandWithResException(NettyError remoteError) {
        Command response = Command.newResponse("0", 0, remoteError);
        throw new NettyCommandWithResException(response);
    }

    @Override
    public void validateCommand(Command request) {
        if(StringUtils.isBlank(request.getToken())) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_VALIDATE_ERROR.format("非法Token"));
        } else {
            setToken(request.getToken());
        }

        if(StringUtils.isBlank(request.getOpaque())) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_VALIDATE_ERROR.format("非法Opaque"));
        }

        if(request.getHeader().getType() == 0) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_VALIDATE_ERROR.format("非法Type"));
        }

    }

    @Override
    public void validateUserId(Command request) {
        Long userId = request.getAuth().getUserId();
        if(userId == null || userId <= 0) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_AUTH_ERROR);
        } else {
            setUserId(userId);
            setToken(request.getToken());
            setAppKey(request.getAuth().getAppKey());
            MdcUtil.setExt(""+userId);
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

