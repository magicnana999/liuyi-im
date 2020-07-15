package com.creolophus.liuyi.service;

import com.creolophus.liuyi.common.config.LiuyiSetting;
import com.creolophus.liuyi.feign.BackendFeign;
import com.creolophus.liuyi.io.SendMessageIn;
import com.creolophus.liuyi.processor.UserClientProcessor;
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
public class MessageService extends NettyBaseService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Resource
    private BackendFeign backendFeign;

    @Resource
    private UserClientProcessor userClientProcessor;

    public Long pushMessage(
            Long messageId, Integer messageType, String messageBody, Long receiverId, Long groupId, Long senderId) {

       return userClientProcessor.pushMessage(messageId, messageType, messageBody, receiverId, groupId, senderId);
    }


    public Long sendMessage(SendMessageIn m) {
        SimpleDateFormat format = new SimpleDateFormat(LiuyiSetting.MESSAGE_SEND_TIME_PATTERN);
        String sendTime = format.format(new Date());
        return backendFeign.sendMessage(getUserId(), m.getMessageType(), m.getTargetId(), sendTime, m.getMessageBody());
    }
}
