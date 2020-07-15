package com.creolophus.liuyi.storage;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.base.BaseStorage;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.liuyi.common.security.UserSecurity;
import com.creolophus.liuyi.common.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Service
public class AuthStorage extends BaseStorage {

    private static final String TOKEN = PREFIX+ "user_token" + SE;

    @Resource
    private RedisClient redisClient;

    public String getToken(String token) {
        return redisClient.get(getTokenKey(token));
    }

    private String getTokenKey(String token) {
        Strings.requireNonBlank(token, "token 为空,无法生成 Group Key");
        return TOKEN + token;
    }

    public String setToken(String token, UserSecurity userSecurity) {
        if(userSecurity==null) return null;
        String ret = redisClient.set(getTokenKey(token), JSON.toJSONString(userSecurity));
        redisClient.expire(getTokenKey(token), DAY * 3);
        return ret;
    }

    public Long delToken(String token){
        return redisClient.del(getTokenKey(token));
    }


}


