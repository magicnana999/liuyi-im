package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.Command;

import javax.websocket.Session;

/**
 * @author magicnana
 * @date 2020/6/30 下午2:48
 */
public interface SessionEventListener {
    /**
     * 断开连接方法
     */
    void onClose(Session session);

    /**
     * 发生异常时触发的方法
     * @param session   session 对象
     * @param throwable 抛出的异常
     */
    void onError(Session session, Throwable throwable);

    /**
     * 发出响应后
     */
    void onFlush(Session session, Command response);

    /**
     * 收到客户端消息后调用的方法
     * @param session session 对象
     * @param message 返回客户端的消息
     */
    void onMessage(Session session, String message);

    /**
     * 连接建立成功调用的方法
     * @param session session 对象
     */
    void onOpen(Session session);

}
