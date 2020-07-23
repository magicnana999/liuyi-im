package com.creolophus.im.storage;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.common.base.BaseStorage;
import com.creolophus.im.common.entity.User;
import com.creolophus.liuyi.common.redis.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class UserStorage extends BaseStorage {

    private static final String USER_INSTANCE = PREFIX + "user_instance" + SE;
    private static final String USER_INSTANCE_LOCK = USER_INSTANCE + "lock" + SE;
    private static final int USER_INSTANCE_EXPIRE = DAY * 7;
    private static final int USER_INSTANCE_LOCK_EXPIRE = SECOND * 3;


    @Resource
    private RedisClient redisClient;

    public User getUserInstance(Long userId) {
        String json = redisClient.get(getUserInstanceKey(userId));
        return JSON.parseObject(json, User.class);
    }

    private String getUserInstanceKey(Long userId) {
        Objects.requireNonNull(userId,"userId 为空,无法生成 User Key");
        return USER_INSTANCE + userId;
    }

    private String getUserInstanceLockKey(Long userId) {
        Objects.requireNonNull(userId,"userId 为空,无法生成 UserInstanceLock Key");
        return USER_INSTANCE_LOCK + userId;
    }

    public boolean lockUserInstance(Long userId) {
        String ret = redisClient.lock(getUserInstanceLockKey(userId), USER_INSTANCE_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public String setUserInstance(User user) {
        if(user==null) return null;
        String ret = redisClient.set(getUserInstanceKey(user.getUserId()), JSON.toJSONString(user));
        redisClient.expire(getUserInstanceKey(user.getUserId()), USER_INSTANCE_EXPIRE);
        return ret;
    }

    public boolean unlockUserInstance(Long userId) {
        return redisClient.unlock(getUserInstanceLockKey(userId), null);
    }

}
