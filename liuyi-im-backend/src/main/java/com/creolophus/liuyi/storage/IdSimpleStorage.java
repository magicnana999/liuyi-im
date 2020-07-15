package com.creolophus.liuyi.storage;

import com.creolophus.liuyi.common.base.BaseStorage;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.liuyi.common.util.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class IdSimpleStorage extends BaseStorage {

    private static final String GROUP_ID = PREFIX + "id_group";
    private static final String GROUP_ID_LOCK = GROUP_ID + "lock" + SE;
    private static final int GROUP_ID_LOCK_EXPIRE = SECOND * 3;


    private static final String MESSAGE_ID = PREFIX + "id_message"+SE;
    private static final String MESSAGE_ID_LOCK = MESSAGE_ID + "lock" + SE;
    private static final int MESSAGE_ID_LOCK_EXPIRE = SECOND * 3;


    @Resource
    private RedisClient redisClient;

    public String getGroupIdKey() {
        return GROUP_ID;
    }

    public String getGroupIdLockKey() {
        return GROUP_ID_LOCK;
    }

    public String getMessageIdKey(Long receiverId){
        return MESSAGE_ID+receiverId;
    }

    public String getMessageIdLockKey(Long receiverId){
        Objects.requireNonNull(receiverId, "receiverId 为空,无法生成 getMessageIdLockKey");
        return MESSAGE_ID_LOCK + receiverId;
    }

    public boolean lockGroupId() {
        String ret = redisClient.lock(getGroupIdLockKey(), GROUP_ID_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public boolean LockMessageId(Long receiverId){
        String ret = redisClient.lock(getMessageIdLockKey(receiverId), MESSAGE_ID_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public Long nextGroupId() {
        return redisClient.incr(getGroupIdKey());
    }

    public Long nextMessageId(Long receiverId){
        return redisClient.incr(getMessageIdKey(receiverId));
    }

    public String setGroupId(Long maxGroupId) {
        return redisClient.set(getGroupIdKey(), String.valueOf(maxGroupId));
    }

    public String setMessageId(Long receiverId,Long maxMessageId){
        return redisClient.set(getMessageIdKey(receiverId), String.valueOf(maxMessageId));
    }

    public boolean unlockGroupId() {
        return redisClient.unlock(getGroupIdLockKey(), null);
    }

    public boolean unlockMessageId(Long receiverId){
        return redisClient.unlock(getMessageIdLockKey(receiverId), null);
    }

}
