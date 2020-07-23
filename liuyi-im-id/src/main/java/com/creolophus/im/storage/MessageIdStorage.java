package com.creolophus.im.storage;

import com.creolophus.im.common.base.BaseStorage;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.im.entity.MessageIdSection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class MessageIdStorage extends BaseStorage {

    private static final String SECTIONID = PREFIX + "message_id" + SE + "section_id";
    private static final String SECTIONID_LOCK = SECTIONID + "lock";

    private static final String SECTION = PREFIX + "message_id" + SE + "section" + SE;
    private static final String SECTION_LOCK = SECTION + "lock" + SE;

    private static final String SECTIONUSER = PREFIX + "message_id" + SE + "section_user" + SE;
    private static final String SECTIONUSER_LOCK = SECTIONUSER + "lock" + SE;

    private static final String FIELD_CURRENTID = "currentId";
    private static final String FIELD_MAXID = "maxId";

    @Resource
    private RedisClient redisClient;

    public void delSection(Long sectionId) {
        redisClient.del(sectionKey(sectionId));

    }

    public Long getCurrentFieldBySectionId(Long sectionId) {
        String currentId = redisClient.hget(sectionKey(sectionId), FIELD_CURRENTID);
        return StringUtils.isBlank(currentId) ? null : Long.valueOf(currentId);
    }

    public Long getMaxFieldBySectionId(Long sectionId) {
        String maxId = redisClient.hget(sectionKey(sectionId), FIELD_MAXID);
        return StringUtils.isBlank(maxId) ? null : Long.valueOf(maxId);
    }

    public MessageIdSection getSection(Long sectionId) {

        String currentId = redisClient.hget(sectionKey(sectionId), FIELD_CURRENTID);
        String maxId = redisClient.hget(sectionKey(sectionId), FIELD_MAXID);

        MessageIdSection messageIdSection = new MessageIdSection();
        messageIdSection.setSectionId(sectionId);
        messageIdSection.setCurrentId(StringUtils.isBlank(currentId) ? null : Long.valueOf(currentId));
        messageIdSection.setMaxId(StringUtils.isBlank(maxId) ? null : Long.valueOf(maxId));
        return messageIdSection;
    }

    public Long getSectionId() {
        String temp = redisClient.get(sectionIdKey());
        if(StringUtils.isBlank(temp)) {
            return null;
        }
        return Long.valueOf(temp);
    }

    public void setSectionId(long initValue) {
        redisClient.set(sectionIdKey(), String.valueOf(initValue));
    }

    public Long getSectionIdByUserId(Long userId) {
        String temp = redisClient.get(sectionUserKey(userId));
        if(StringUtils.isBlank(temp)) {
            return null;
        }
        return Long.valueOf(temp);
    }

    public Long incrCurrentId(Long sectionId, long batch) {
        long id = redisClient.hincrBy(sectionKey(sectionId), FIELD_CURRENTID, batch);
        return id;
    }

    public Long incrSectionId() {
        return redisClient.incr(sectionIdKey());
    }

    public boolean lockSetSection(Long sectionId) {
        String sign = redisClient.lock(sectionLockKey(sectionId), HOUR * 3);
        return StringUtils.isNotBlank(sign);
    }

    public boolean lockSetSectionId() {
        String sign = redisClient.lock(sectionIdLockKey(), HOUR * 3);
        return StringUtils.isNotBlank(sign);
    }

    public boolean lockSetSectionUser(Long userId) {
        String sign = redisClient.lock(sectionUserLockKey(userId), HOUR * 3);
        return StringUtils.isNotBlank(sign);
    }

    private String sectionIdKey() {
        return SECTIONID;
    }

    private String sectionIdLockKey() {
        return SECTIONID_LOCK;
    }

    private String sectionKey(Long sectionId) {
        return SECTION + sectionId;
    }

    private String sectionLockKey(Long sectionId) {
        return SECTION_LOCK + sectionId;
    }

    private String sectionUserKey(Long userId) {
        return SECTIONUSER + userId;
    }

    private String sectionUserLockKey(Long userId) {
        return SECTIONUSER_LOCK + userId;
    }

    public void setSectionHash(MessageIdSection messageIdSection) {
        redisClient.hset(sectionKey(messageIdSection.getSectionId()), FIELD_CURRENTID, String.valueOf(messageIdSection.getCurrentId()));
        redisClient.hset(sectionKey(messageIdSection.getSectionId()), FIELD_MAXID, String.valueOf(messageIdSection.getMaxId()));
    }

    public void setSectionUser(Long userId, Long sectionId) {
        redisClient.set(sectionUserKey(userId), String.valueOf(sectionId));
//        redisClient.expire(sectionUserKey(userId), MINUTE * 30);
    }

    public void unlockSetSection(Long sectionId) {
        redisClient.unlock(sectionLockKey(sectionId), null);
    }

    public void unlockSetSectionId() {
        redisClient.unlock(sectionIdLockKey(), null);
    }

    public void unlockSetSectionUser(Long userId) {
        redisClient.unlock(sectionUserLockKey(userId), null);
    }

}
