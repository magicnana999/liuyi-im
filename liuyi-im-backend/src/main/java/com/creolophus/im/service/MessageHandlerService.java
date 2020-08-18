package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.Message;
import com.creolophus.im.dao.MessageDao;
import com.creolophus.im.domain.GatewayAddr;
import com.creolophus.im.storage.MessageStorage;
import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.liuyi.common.logger.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2020/7/3 下午4:58
 */
@Service
public class MessageHandlerService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerService.class);

    @Resource
    private MessageStorage messageStorage;

    @Resource
    private MessageDao messageDao;

    @Resource
    private UserClientService userClientService;

    @Resource
    private MessagePushService messagePushService;

    @Entry
    public Long asyncMessage() {
        List<String> list = messageStorage.popAsyncMessage();
        if(list != null && list.size() == 2) {
            Message message = JSON.parseObject(list.get(1), Message.class);
            logger.debug("同步消息 {}", message.getMessageId());
            pushMessage(message);
            saveMessage(message);
        }
        return 0L;
    }

    private String pushMessage(Message message) {
        if(message == null || message.getReceiverId() == null) return "0";
        GatewayAddr gatewayAddr = userClientService.findUserClient(message.getReceiverId());
        if(gatewayAddr != null) return messagePushService.push(gatewayAddr.getIp(), gatewayAddr.getPort(), message);
        return "0";
    }

    private Long saveMessage(Message message) {
        messageDao.insert(message, false);
        return message == null ? 0L : message.getMessageId();
    }

}
