package com.creolophus.liuyi.boot;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.liuyi.common.api.GlobalSetting;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.netty.core.ContextProcessor;
import com.creolophus.liuyi.netty.exception.NettyCommandWithResException;
import com.creolophus.liuyi.netty.exception.NettyError;
import com.creolophus.liuyi.netty.protocol.Command;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2019/8/12 上午10:58
 */
public class NettyContextValidator extends ApiContextValidator implements ContextProcessor {

    private static final String APPKEY = "APPKEY";
    private static final String CHANNEL = "SESSION";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

    private static final Logger logger = LoggerFactory.getLogger(NettyContextValidator.class);

    @Resource
    private GlobalSetting globalSetting;

    public NettyContextValidator() {
    }

    @Override
    public String getAppKey() {
        return ApiContext.getContext().getExt(APPKEY);
    }

    @Override
    public void setAppKey(String appKey) {
        ApiContext.getContext().setExt(APPKEY, appKey);
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
        return ApiContext.getContext().getExt(CHANNEL);
    }

    @Override
    public void setChannel(Channel channel) {
        ApiContext.getContext().setExt(CHANNEL, channel);
    }

    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public void setSession(Session session) {
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

    public void setUserId(Long userId) {
        ApiContext.getContext().setUserId(userId);
    }

    @Override
    public void initContext(Channel channel, Command command) {

        if(command == null) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_NULL);
        }

        if(command.isEmpty()) {
            throwRemotingCommandWithResException(NettyError.E_REQUEST_BODY_NULL);
        }


        setChannel(channel);
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
    public void initContext(Session session, Command command) {

    }

    @Override
    public void clearContext() {
        MdcUtil.clearAll();
        super.cleanContext();
    }

    public void throwRemotingCommandWithResException(NettyError remoteError) {
        Command response = Command.newResponse("0", 0, remoteError);
        throw new NettyCommandWithResException(response);
    }

    public void trace() {
        logger.info("{} {}", JSON.toJSONString(getRequest()), JSON.toJSONString(getResponse()));
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
}
