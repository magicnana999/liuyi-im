package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.dao.GroupDao;
import com.creolophus.im.dao.MessageDao;
import com.creolophus.im.storage.IdSimpleStorage;
import com.creolophus.im.storage.MessageStorage;
import com.creolophus.liuyi.common.exception.ApiException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/6/19 下午4:21
 */
@Service
public class IdSimpleService extends BaseService {

    @Resource
    private SnowFlakeGenerator snowFlakeGenerator;

    @Resource
    private IdSimpleStorage idSimpleStorage;

    @Resource
    private MessageStorage messageStorage;

    @Resource
    private GroupDao groupDao;

    @Resource
    private MessageDao messageDao;

    public Long nextGroupId(Long userId) {
        Long nextId = idSimpleStorage.nextGroupId();

        if(nextId < 2) {
            if(idSimpleStorage.lockGroupId()) {
                try {
                    Long maxId = groupDao.selectMaxGroupId();
                    if(maxId != null && maxId > 0) {
                        idSimpleStorage.setGroupId(maxId);
                        nextId = idSimpleStorage.nextGroupId();
                    }
                } finally {
                    idSimpleStorage.unlockGroupId();
                }
            } else {
                throw new ApiException("无法取得 GroupId");
            }
        }
        return nextId;

    }

    public Long nextMessageId(Long receiverId) {
        Long nextId = idSimpleStorage.nextMessageId(receiverId);
        if(nextId < 2) {
            if(idSimpleStorage.LockMessageId(receiverId)) {
                try {
                    Long maxId = messageStorage.getMaxMessageId(receiverId);
                    if(maxId != null && maxId > 0) {
                        idSimpleStorage.setMessageId(receiverId, maxId);
                        nextId = idSimpleStorage.nextMessageId(receiverId);
                    } else {
                        maxId = messageDao.selectMaxMessageId(receiverId);
                        if(maxId != null && maxId > 0) {
                            idSimpleStorage.setMessageId(receiverId, maxId);
                            nextId = idSimpleStorage.nextMessageId(receiverId);
                        }
                    }
                } finally {
                    idSimpleStorage.unlockMessageId(receiverId);
                }
            } else {
                throw new ApiException("同一个 UserId 不能同时取得 MessageId");
            }
        }
        return nextId;
    }

    public Long nextUserId() {
        return snowFlakeGenerator.nextId();
    }
}
