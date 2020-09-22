package com.creolophus.tomato.storage;

import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.tomato.base.BaseStorage;
import com.creolophus.tomato.entity.TomatoUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class UserStorage extends BaseStorage {

    private static final String USER_INSTANCE = PREFIX + "user_instance" + SE;
    private static final String USER_INSTANCE_LOCK = USER_INSTANCE + "lock" + SE;

    private static final int USER_INSTANCE_EXPIRE = MINUTE * 10;
    private static final int USER_INSTANCE_LOCK_EXPIRE = SECOND;


    private static final String USER_INFO = PREFIX + "user_info" + SE;
    private static final String USER_INFO_LOCK = USER_INSTANCE + "lock" + SE;

    private static final int USER_INFO_EXPIRE = MINUTE * 10;
    private static final int USER_INFO_LOCK_EXPIRE = SECOND;


    @Resource
    private RedisClient redisClient;

    private String buildUserInfoKey(String phone) {
        return USER_INFO + phone;
    }

    private String buildUserInfoLockKey(String phone) {
        return USER_INFO_LOCK + phone;
    }

    private String buildUserInstanceKey(Long userId) {
        return USER_INSTANCE + userId;
    }

    private String buildUserInstanceLockKey(Long userId) {
        return USER_INSTANCE_LOCK + userId;
    }

    public TomatoUser getUserInfo(String phone) {
        String json = redisClient.get(buildUserInfoKey(phone));
        return JSON.parseObject(json, TomatoUser.class);
    }

    public TomatoUser getUserInstance(Long userId) {
        String json = redisClient.get(buildUserInstanceKey(userId));
        return JSON.parseObject(json, TomatoUser.class);
    }

    public boolean lockUserInfo(String phone) {
        String ret = redisClient.lock(buildUserInfoLockKey(phone), USER_INFO_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public boolean lockUserInstance(Long userId) {
        String ret = redisClient.lock(buildUserInstanceLockKey(userId), USER_INSTANCE_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public String setUserInfo(TomatoUser tomatoUser) {
        if(tomatoUser == null) {
            return null;
        }
        String ret = redisClient.set(buildUserInfoKey(tomatoUser.getPhone()), JSON.toJSONString(tomatoUser));
        redisClient.expire(buildUserInfoKey(tomatoUser.getPhone()), USER_INFO_EXPIRE);
        return ret;
    }

    public String setUserInstance(TomatoUser tomatoUser) {
        if(tomatoUser == null) {
            return null;
        }
        String ret = redisClient.set(buildUserInstanceKey(tomatoUser.getUserId()), JSON.toJSONString(tomatoUser));
        redisClient.expire(buildUserInstanceKey(tomatoUser.getUserId()), USER_INSTANCE_EXPIRE);
        return ret;
    }

    public boolean unlockUserInfo(String phone) {
        return redisClient.unlock(buildUserInfoLockKey(phone), null);
    }

    public boolean unlockUserInstance(Long userId) {
        return redisClient.unlock(buildUserInstanceLockKey(userId), null);
    }

}
