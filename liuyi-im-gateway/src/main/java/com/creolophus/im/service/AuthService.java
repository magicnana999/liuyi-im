package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.security.UserSecurity;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.protocol.domain.Auth;
import com.creolophus.im.protocol.domain.UserTest;
import com.creolophus.liuyi.common.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/1/11 下午5:14
 */
@Service
public class AuthService extends BaseService {

    @Resource
    private BackendFeign backendFeign;

    private UserSecurity prod(String token) {
        return backendFeign.verifyToken(token);
    }

    private UserSecurity test(String token) {
        UserSecurity us = new UserSecurity();
        us.setAppKey(UserTest.appKey);
        if(token.equals(UserTest.张无忌.token)) {
            us.setUserId(UserTest.张无忌.userId);
        } else if(token.equals(UserTest.周芷若.token)) {
            us.setUserId(UserTest.周芷若.userId);
        } else if(token.equals(UserTest.赵敏.token)) {
            us.setUserId(UserTest.赵敏.userId);
        } else {
            us.setUserId(UserTest.小昭.userId);
        }
        return us;
    }

    public Auth verify(String token) {

        UserSecurity us = test(token);
        if(us == null) {
            throw new UnauthorizedException("无法取得 token 信息");
        }
        Auth auth = new Auth();
        auth.setAppKey(us.getAppKey());
        auth.setUserId(us.getUserId());
        return auth;
    }
}
