package com.creolophus.im.service;

import com.creolophus.im.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//import com.creolophus.liuyi.netty.context.RemoteContextValidator;

/**
 * @author magicnana
 * @date 2019/1/23 下午5:38
 */
@Service
public abstract class UserClientService {

    private static final Logger logger = LoggerFactory.getLogger(UserClientService.class);

    public abstract LoginDown login(LoginUp input);

    public PushMessageDown pushMessage(PushMessageDown pushMessageDown) {
        Command response = Command.newRequest(CommandType.PUSH_MESSAGE.getValue(), pushMessageDown);
        pushMessage(pushMessageDown.getReceiverId(), response);
        logger.info("消息推送 完成 {} -> {}.{}", pushMessageDown.getSenderId(), pushMessageDown.getReceiverId(), pushMessageDown.getMessageId());

        return pushMessageDown;
    }

    public abstract void pushMessage(Long receiverId, Command response);

    public void pushMessageAck(PushMessageUp pushMessageUp) {
        logger.info("消息推送 到达 {} -> {}.{}", pushMessageUp.getSenderId(), pushMessageUp.getReceiverId(), pushMessageUp.getMessageId());
    }

}
