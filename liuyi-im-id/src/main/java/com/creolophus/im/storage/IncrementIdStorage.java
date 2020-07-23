package com.creolophus.im.storage;

import com.creolophus.im.common.base.BaseStorage;
import com.creolophus.liuyi.common.redis.RedisClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class IncrementIdStorage extends BaseStorage {

    private static final String INCREMENT_WITH_USERID = PREFIX + "message_id" + SE + "increment" + SE;

    @Resource
    private RedisClient redisClient;

    private String getIncrementWithUserIdKey(Long userId) {
        return INCREMENT_WITH_USERID + userId;
    }

    public Long increase(Long userId) {
        return redisClient.incr(getIncrementWithUserIdKey(userId));
    }

    public Long increase(Long userId, long batch) {
        return redisClient.incrBy(getIncrementWithUserIdKey(userId), batch);
    }
}
