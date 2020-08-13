package com.creolophus.im.websocket;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.netty.core.AbstractWebSocketServer;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.netty.sleuth.SleuthNettyAdapter;
import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.api.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author magicnana
 * @date 2020/7/8 下午8:17
 */
@ServerEndpoint("/liuyi/gateway/ws")
@Component
public class WebsocketServerInstance extends AbstractWebSocketServer {

    private Logger logger = LoggerFactory.getLogger(WebsocketServerInstance.class);

    private Session session;

    private String getErrorMessage(Throwable e) {
        String msg = e.getMessage();
        if(StringUtils.isBlank(msg)) {
            return "未知异常,没有消息";
        }

        try {
            ApiResult apiResult = JSON.parseObject(msg, ApiResult.class);
            if(apiResult == null || StringUtils.isBlank(apiResult.getMessage())) {
                return "未知错误,木有消息";
            } else {
                return apiResult.getMessage();
            }
        } catch (Throwable throwable) {
            return msg;
        }
    }

    @Override
    public Session getSession() {
        return session;
    }

    @OnClose
    public void onClose(Session session) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onClose");
        logger.debug("{}", session.getId());
//        connect(session);         //客服不需要关闭闲置连接
        if(sessionEventListener != null) sessionEventListener.onClose(session);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onError");
        logger.error("{}", session.getId(), error);


        Command request = contextProcessor.getRequest();
        Command response = Command.newResponse(request.getHeader().getSeq(), request.getHeader().getType(), NettyError.E_ERROR);
        response(session, response);
        if(sessionEventListener != null) sessionEventListener.onError(session, error);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();

    }

    @OnMessage
    public void onMessage(Session session, String message) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onMessage");
        logger.debug("{} {}", session.getId(), message);
        Command request = decode(message);
        contextProcessor.initContext(session, request);
        verify(session, request);
        contextProcessor.validateAfterVerify(request);
        if(sessionEventListener != null) sessionEventListener.onMessage(session, message);
        Command response = process(session, request);
        flush(session, message, response);
        if(sessionEventListener != null) sessionEventListener.onFlush(session, response);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }

    @OnOpen
    public void onOpen(Session session) {
        SleuthNettyAdapter.getInstance().begin(tracerUtil, "onOpen");
        logger.debug("{}", session.getId());
        this.session = session;
        if(sessionEventListener != null) sessionEventListener.onOpen(session);
        SleuthNettyAdapter.getInstance().cleanContext();
        contextProcessor.clearContext();
    }
}
