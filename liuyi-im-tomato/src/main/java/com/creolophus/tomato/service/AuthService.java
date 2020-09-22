package com.creolophus.tomato.service;

import com.creolophus.liuyi.common.codec.MD5Util;
import com.creolophus.liuyi.common.exception.ApiException;
import com.creolophus.liuyi.common.exception.UnauthorizedException;
import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.tomato.base.BaseService;
import com.creolophus.tomato.dao.TomatoUserDao;
import com.creolophus.tomato.entity.TomatoUser;
import com.creolophus.tomato.feign.BackendFeign;
import com.creolophus.tomato.storage.AuthStorage;
import com.creolophus.tomato.vo.UserToken;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Resource
    private UserIdService userIdService;


    @Resource
    private BackendFeign backendFeign;

    @Resource
    private TomatoUserDao tomatoUserDao;

    private boolean checkCode(String phone, String code) {
        String codeInRedis = authStorage.getCode(phone);
        if(StringUtils.isBlank(codeInRedis)){
            throw new ApiException("请先取得验证码");
        }
        if(!codeInRedis.equalsIgnoreCase(code)) {
            throw new ApiException("验证码错误");
        }
        return true;
    }

    private String createToken(Long userId) {
        return MD5Util.md5Hex("" + userId + System.currentTimeMillis() + Thread.currentThread() + RandomUtils.nextLong(0, 99999));
    }

    public TomatoUser createUser(String name, String portrait, String phone,String password) {
        TomatoUser tomatoUser = new TomatoUser();
        tomatoUser.setPhone(phone);
        tomatoUser.setState(TomatoUser.State.ENABLE.getValue());
        tomatoUser.setUserId(userIdService.nextId());
        tomatoUser.setCreateTime(new Date());
        tomatoUser.setName(name);
        tomatoUser.setPortrait(portrait);
        tomatoUser.setPassword(password);
        return tomatoUser;
    }

    public String getRegCode(String phone) {
        return authStorage.getCode(phone);
    }

    public UserToken login(String phone, String code) {
        checkCode(phone, code);
        if(authStorage.lockLogin(phone)) {
            try {
                TomatoUser tomatoUser = userService.findUserByPhone(phone);
                if(tomatoUser == null) {
                    throw new ApiException("无此手机号码");
                }
                UserToken token = login(tomatoUser);
                authStorage.delCode(phone);
                return token;
            } finally {
                authStorage.unlockLogin(phone);
            }
        } else {
            throw new ApiException("稍后再试");
        }
    }

    private UserToken login(TomatoUser tomatoUser) {
        String imToken = loginIm(tomatoUser.getImId());
        UserToken token = loginTomato(tomatoUser.getUserId(), tomatoUser.getImId(), imToken);
        return token;
    }

    private String loginIm(Long imId) {
        return backendFeign.login(imId);
    }

    private UserToken loginTomato(Long userId, Long imId, String imToken) {
        String token = createToken(userId);
        UserToken userToken = new UserToken(userId, imId, token, imToken);
        authStorage.setToken(userToken);
        return userToken;
    }

    public int pushRegCode(String phone) {
        int code = RandomUtils.nextInt(1234, 9934);
        authStorage.setCode(phone, code);
        return code;
    }

    @Transactional(rollbackFor = {Exception.class})
    public UserToken register(String phone, String name, String portrait, String password) {
        if(authStorage.lockRegister(phone)) {
            try {
                TomatoUser tomatoUser = createUser(name, portrait, phone, password);
                Long imId = syncUser(tomatoUser);
                tomatoUser.setImId(imId);
                tomatoUserDao.insert(tomatoUser);
                UserToken token = login(tomatoUser);
                authStorage.delCode(phone);
                return token;
            } finally {
                authStorage.unlockRegister(phone);
            }
        } else {
            throw new ApiException("稍后再试");
        }
    }

    private Long syncUser(TomatoUser tomatoUser) {
        return backendFeign.syncUser(tomatoUser.getName(), tomatoUser.getPortrait(), tomatoUser.getUserId());
    }

    public UserToken verifyToken(String token) {
        String json = authStorage.getToken(token);
        if(StringUtils.isBlank(json)) {
            throw new UnauthorizedException("无此 token");
        }
        UserToken user = JSON.parseObject(json, UserToken.class);
        if(user == null || user.isEmpty()) {
            authStorage.delToken(token);
            throw new UnauthorizedException("非法 token");
        }
        return user;
    }

}
