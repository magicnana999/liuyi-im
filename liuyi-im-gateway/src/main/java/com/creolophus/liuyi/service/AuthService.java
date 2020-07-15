package com.creolophus.liuyi.service;

import com.creolophus.liuyi.common.base.BaseService;
import com.creolophus.liuyi.common.exception.UnauthorizedException;
import com.creolophus.liuyi.common.security.UserSecurity;
import com.creolophus.liuyi.feign.BackendFeign;
import com.creolophus.liuyi.netty.protocol.Auth;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/1/11 下午5:14
 */
@Service
public class AuthService extends BaseService {

    private static final long 张无忌 = 11;
    private static final long 赵敏 = 12;
    private static final long 周芷若 = 13;
    private static final long 小昭 = 14;

    private static final String 老张 = "e912782f1ee0612220c17214aaeb2892";
    private static final String 老赵 = "15907024f9ecb4b4a5f088f94d25ce26";
    private static final String 老周 = "7be9166d6072cbeec51af0b618896fe3";
    private static final String 老小 = "054449cf09cdc407f558e2d64d4df595";


    @Resource
    private BackendFeign backendFeign;

    public Auth verify(String token) {

        UserSecurity us = new UserSecurity();
        if(token.equals(老张)){
            us = new UserSecurity();
            us.setAppKey("aa");
            us.setUserId(张无忌);
        }else{
            us = new UserSecurity();
            us.setAppKey("aa");
            us.setUserId(赵敏);
        }

//        UserSecurity us = backendFeign.verifyToken(token);
        if(us == null) {
            throw new UnauthorizedException("无法取得 token 信息");
        }
        Auth auth = new Auth();
        auth.setAppKey(us.getAppKey());
        auth.setUserId(us.getUserId());
        return auth;
    }
}
