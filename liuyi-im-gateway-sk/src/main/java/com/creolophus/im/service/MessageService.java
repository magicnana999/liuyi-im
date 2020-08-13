package com.creolophus.im.service;

import com.creolophus.im.common.config.LiuyiSetting;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.processor.MessageProcessor;
import com.creolophus.im.protocol.SendMessageDown;
import com.creolophus.im.protocol.SendMessageUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author magicnana
 * @date 2019/9/16 上午10:12
 */
@Service
public class MessageService extends NettyBaseService implements MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Resource
    private BackendFeign backendFeign;

    @Override
    public SendMessageDown sendMessage(SendMessageUp m) {
        SimpleDateFormat format = new SimpleDateFormat(LiuyiSetting.MESSAGE_SEND_TIME_PATTERN);
        String sendTime = format.format(new Date());
        Long messageId = backendFeign.sendMessage(getAppKey(), getUserId(), m.getMessageType(), m.getTargetId(), sendTime, m.getMessageBody());
        return new SendMessageDown(messageId);
    }
}
