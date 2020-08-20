package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.Message;
import com.creolophus.im.dao.MessageDao;
import com.creolophus.im.storage.MessageStorage;
import com.creolophus.im.thread.StopableThread;
import com.creolophus.im.type.MessageType;
import com.creolophus.im.vo.SimpleMessageVo;
import com.creolophus.liuyi.common.exception.ApiException;
import com.creolophus.liuyi.common.thread.Stopable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class MessageService extends BaseService implements Stopable {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private static List<StopableThread> threads = new ArrayList<>();

    @Resource
    private IdSimpleService idSimpleService;

    @Resource
    private GroupService groupService;

    @Resource
    private MessageStorage messageStorage;

    @Resource
    private MessageDao messageDao;

    @Resource
    private MessageHandlerService messageHandlerService;

    private Long asyncMessage(Message message) {
        return messageStorage.asyncMessage(message);
    }

    public List<SimpleMessageVo> findMessageList(Long receiverId, Long messageId, Integer messageType, Long sourceId, int pageNo, int pageSize) {

        MessageType mt = MessageType.valueOf(messageType);
        if(mt == null) {
            throw new ApiException("无此 MessageType");
        }

        if(mt.value() == MessageType.SINGLE.value()) {
            return findMessageListOfSingle(receiverId, messageId, messageType, sourceId, pageNo, pageSize);
        } else {
            return findMessageListOfGroup(receiverId, messageId, messageType, sourceId, pageNo, pageSize);
        }
    }

    private List<SimpleMessageVo> findMessageListOfGroup(Long receiverId, Long messageId, Integer messageType, Long groupId, int pageNo, int pageSize) {
        return messageDao.createLambdaQuery()
                .andEq(Message::getReceiverId, receiverId)
                .andGreat(Message::getMessageId, messageId)
                .andEq(Message::getMessageType, messageType)
                .andEq(Message::getGroupId, groupId)
                .asc(Message::getMessageId)
                .limit((pageNo - 1) * pageSize, pageSize)
                .select(SimpleMessageVo.class, null);
    }

    private List<SimpleMessageVo> findMessageListOfSingle(Long receiverId, Long messageId, Integer messageType, Long senderId, int pageNo, int pageSize) {
        return messageDao.createLambdaQuery()
                .andEq(Message::getReceiverId, receiverId)
                .andGreat(Message::getMessageId, messageId)
                .andEq(Message::getMessageType, messageType)
                .andEq(Message::getSenderId, senderId)
                .asc(Message::getMessageId)
                .limit((pageNo - 1) * pageSize, pageSize)
                .select(SimpleMessageVo.class, null);
    }

    @PostConstruct
    public void initThread() {
        Runnable asyncRunnable = () -> messageHandlerService.asyncMessage();
        int processors = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < processors; i++) {
            StopableThread t = new StopableThread(asyncRunnable);
            threads.add(t);
            t.start();
        }

        logger.info("所有线程已启动 {}", threads.size());
    }

    public Long sendMessage(Long senderId, Long targetId, Integer messageType, String messageBody, Date sendTime, String appKey) {
        if(messageType == MessageType.SINGLE.value()) {
            Message message = new Message();
            message.setMessageId(idSimpleService.nextMessageId(targetId));
            message.setCreateTime(new Date());
            message.setMessageBody(messageBody);
            message.setMessageType(messageType);
            message.setSenderId(senderId);
            message.setSendTime(sendTime);
            message.setReceiverId(targetId);
            message.setAppKey(appKey);
            asyncMessage(message);
            return message.getMessageId();
        } else {
            List<Long> list = groupService.findMembersUserIdByGroupId(targetId);
            Long messageId = 0L;
            for (Long member : list) {
                Message message = new Message();
                message.setMessageId(idSimpleService.nextMessageId(member));
                message.setCreateTime(new Date());
                message.setGroupId(targetId);
                message.setMessageBody(messageBody);
                message.setMessageType(messageType);
                message.setSenderId(senderId);
                message.setSendTime(sendTime);
                message.setReceiverId(member);
                message.setAppKey(appKey);
                asyncMessage(message);

                if(member.equals(senderId)) {
                    messageId = message.getMessageId();
                }
            }
            return messageId;
        }
    }

    @Override
    public void shutdown() {
        threads.forEach(t -> {
            t.shutdown();
        });
    }
}
