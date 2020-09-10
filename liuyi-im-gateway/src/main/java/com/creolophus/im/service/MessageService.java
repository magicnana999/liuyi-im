package com.creolophus.im.service;

import com.creolophus.im.common.config.LiuyiSetting;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.type.SendMessageAck;
import com.creolophus.im.type.SendMessageMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author magicnana
 * @date 2020/8/14 3:41 PM
 */
@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Resource
    protected ContextProcessor contextProcessor;

    @Resource
    protected BackendFeign backendFeign;

    protected String getAppKey() {
        return contextProcessor.getAppKey();
    }

    protected long getUserId() {
        return contextProcessor.getUserId();
    }

    public SendMessageAck sendMessage(SendMessageMsg m) {
        SimpleDateFormat format = new SimpleDateFormat(LiuyiSetting.MESSAGE_SEND_TIME_PATTERN);
        String sendTime = format.format(new Date());
        Long messageId = backendFeign.sendMessage(getAppKey(), getUserId(), m.getMessageType(), m.getMessageKind(), m.getTargetId(), sendTime,
                                                  m.getMessageBody());
        logger.info("消息发送 完成 {} -> {}.{}", getUserId(), m.getTargetId(), messageId);
        return new SendMessageAck(messageId);
    }

}
