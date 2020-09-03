package com.creolophus.im.service;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.protocol.domain.Command;
import com.creolophus.im.protocol.domain.CommandType;
import com.creolophus.im.protocol.type.LoginAck;
import com.creolophus.im.protocol.type.LoginMsg;
import com.creolophus.im.protocol.type.PushMessageAck;
import com.creolophus.im.protocol.type.PushMessageMsg;
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

    public abstract LoginAck login(LoginMsg input);

    public Long pushMessage(PushMessageMsg pushMessageMsg) {
        Command response = Command.newMsg(CommandType.PUSH_MESSAGE.value(), pushMessageMsg);

        if(logger.isDebugEnabled()) {
            logger.debug("发送命令 {}", JSON.toJSONString(response));
        }
        pushMessage(pushMessageMsg.getReceiverId(), response);
        logger.info("消息推送 {} -> {}.{}", pushMessageMsg.getSenderId(), pushMessageMsg.getReceiverId(), pushMessageMsg.getMessageId());

        return pushMessageMsg.getMessageId();
    }

    public abstract void pushMessage(Long receiverId, Command response);

    public void pushMessageAck(PushMessageAck pushMessageAck) {
        logger.info("消息到达 {} -> {}.{}", pushMessageAck.getSenderId(), pushMessageAck.getReceiverId(), pushMessageAck.getMessageId());
    }

}
