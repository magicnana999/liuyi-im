package com.creolophus.im.boot;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.netty.core.AbstractContextProcessor;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.GlobalSetting;
import com.creolophus.liuyi.common.api.MdcUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2019/8/12 上午10:58
 */
public class NettyContextValidator extends AbstractContextProcessor implements ContextProcessor {

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
    public void initContext(Channel channel, Command command) {

        setChannel(channel);
        setRequest(command);

        validateCommand(command);
        MdcUtil.setUri("" + command.getHeader().getType());

        if(globalSetting != null && globalSetting.isDebug()) {
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

    public void trace() {
        logger.info("{} {}", JSON.toJSONString(getRequest()), JSON.toJSONString(getResponse()));
    }

    @Override
    public void validateAfterVerify(Command request) {
        super.validateAfterVerify(request);
        setUserId(request.getAuth().getUserId());
        setToken(request.getToken());
        setAppKey(request.getAuth().getAppKey());
        MdcUtil.setExt("" + request.getAuth().getUserId());
    }
}
