package com.creolophus.tomato.storage;

import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.tomato.base.BaseStorage;
import com.creolophus.tomato.vo.UserToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Service
public class AuthStorage extends BaseStorage {

    private static final String TOKEN = PREFIX + "user_token" + SE;
    private static final String CODE = PREFIX + "user_code" + SE;
    private static final String REGISTER_LOCK = PREFIX + "user_register_lock" + SE;
    private static final String LOGIN_LOCK = PREFIX + "user_login_lock" + SE;

    private static final int EXPIRE_CODE = MINUTE * 5;
    private static final int EXPIRE_TOKEN = MINUTE * 10;
    private static final int EXPIRE_LOCK = SECOND;


    @Resource
    private RedisClient redisClient;

    private String buildCodeKey(String phone) {
        return CODE + phone;
    }

    private String buildLoginLockKey(String phone) {
        return LOGIN_LOCK + phone;
    }

    private String buildRegisterLockKey(String phone) {
        return REGISTER_LOCK + phone;
    }

    private String buildTokenKey(String token) {
        return TOKEN + token;
    }

    public Long delCode(String phone) {
        return redisClient.del(buildCodeKey(phone));
    }

    public Long delToken(String token) {
        return redisClient.del(buildTokenKey(token));
    }

    public String getCode(String phone) {
        return redisClient.get(buildCodeKey(phone));
    }

    public String getToken(String token) {
        String json = redisClient.get(buildTokenKey(token));
        return json;
    }

    public boolean lockLogin(String phone) {
        String ret = redisClient.lock(buildLoginLockKey(phone), EXPIRE_LOCK);
        return StringUtils.isNotBlank(ret);
    }

    public boolean lockRegister(String phone) {
        String ret = redisClient.lock(buildRegisterLockKey(phone), EXPIRE_LOCK);
        return StringUtils.isNotBlank(ret);
    }

    public String setCode(String phone, int code) {
        String ret = redisClient.set(buildCodeKey(phone), String.valueOf(code));
        redisClient.expire(buildCodeKey(phone), EXPIRE_CODE);
        return ret;
    }

    public String setToken(UserToken userToken) {
        if(userToken == null) {
            return null;
        }
        String ret = redisClient.set(buildTokenKey(userToken.getToken()), JSON.toJSONString(userToken));
        redisClient.expire(buildTokenKey(userToken.getToken()), EXPIRE_TOKEN);
        return ret;
    }

    public Long unlockLogin(String phone) {
        return redisClient.del(buildLoginLockKey(phone));
    }

    public Long unlockRegister(String phone) {
        return redisClient.del(buildRegisterLockKey(phone));
    }
}


