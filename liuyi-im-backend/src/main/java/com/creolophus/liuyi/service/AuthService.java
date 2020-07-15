package com.creolophus.liuyi.service;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.base.BaseService;
import com.creolophus.liuyi.common.entity.User;
import com.creolophus.liuyi.common.exception.UnauthorizedException;
import com.creolophus.liuyi.common.security.TokenUtil;
import com.creolophus.liuyi.common.security.UserSecurity;
import com.creolophus.liuyi.storage.AuthStorage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author magicnana
 * @date 2019/6/5 上午12:00
 */
@Service
public class AuthService extends BaseService {

    @Resource
    private AuthStorage authStorage;

    @Resource
    private UserService userService;

    public UserSecurity createToken(Long userId) {
        User user = userService.findUserByUserId(userId);
        UserSecurity us = new UserSecurity();
        us.setTimestamp(System.currentTimeMillis());
        us.setToken(TokenUtil.token(userId));
        us.setAppKey(user.getAppKey());
        us.setUserId(user.getUserId());
        us.setRoles(Arrays.asList("ROLE_USER"));

        authStorage.setToken(us.getToken(), us);
        return us;
    }

    public UserSecurity verifyToken(String token) {
        String us = authStorage.getToken(token);
        if(StringUtils.isBlank(us)) {
            throw new UnauthorizedException("无此 token");
        }

        UserSecurity user = JSON.parseObject(us, UserSecurity.class);
        if(user == null) {
            throw new UnauthorizedException("非法 token");
        }
        return user;
    }

}
