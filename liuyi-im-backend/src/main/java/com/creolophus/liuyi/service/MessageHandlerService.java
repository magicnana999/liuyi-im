package com.creolophus.liuyi.service;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.base.BaseService;
import com.creolophus.liuyi.common.entity.Message;
import com.creolophus.liuyi.common.logger.Entry;
import com.creolophus.liuyi.dao.MessageDao;
import com.creolophus.liuyi.domain.GatewayAddr;
import com.creolophus.liuyi.storage.MessageStorage;
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


    private String pushMessage(Message message) {
        if(message == null || message.getReceiverId() == null) return "0";
        GatewayAddr gatewayAddr = userClientService.findUserClient(message.getReceiverId());
        if(gatewayAddr != null) return messagePushService.push(gatewayAddr.getIp(), gatewayAddr.getPort(), message);
        return "0";
    }

    private Long saveMessage(Message message){
        messageDao.insert(message,false);
        return message==null?0L:message.getMessageId();
    }

    @Entry
    public Long asyncMessage() {
        List<String> list = messageStorage.popAsyncMessage();
        if(list!=null && list.size()==2){
            Message message = JSON.parseObject(list.get(1),Message.class);
            logger.debug("同步消息 {}", message.getMessageId());
            pushMessage(message);
            saveMessage(message);
        }
        return 0L;
    }

}
