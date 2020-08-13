package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.User;
import com.creolophus.im.dao.UserDao;
import com.creolophus.im.storage.UserStorage;
import com.creolophus.liuyi.common.codec.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class UserService extends BaseService {

    @Resource
    private IdExampleService idExampleService;

    @Resource
    private UserDao userDao;

    @Resource
    private UserStorage userStorage;

    public Long createUser(String name, String portrait, Long outId, String appKey) {
        User user = new User();
        user.setState(User.State.ENABLE.getValue());
        user.setUserId(idExampleService.nextUserId());
        user.setCreateTime(new Date());
        user.setName(name);
        user.setPortrait(portrait);
        user.setOutId(outId);
        user.setAppKey(appKey);
        userDao.insert(user);
        return user.getUserId();
    }

    public User findUserByUserId(Long userId) {
        User user = userStorage.getUserInstance(userId);
        if(user == null) {
            if(userStorage.lockUserInstance(userId)) {
                try {
                    user = userDao.single(userId);
                    if(user != null) {
                        userStorage.setUserInstance(user);
                    }
                } finally {
                    userStorage.unlockUserInstance(userId);
                }
            }
        }
        return user;
    }

    private String salt(Long userId, String password) {
        return MD5Util.md5Hex(password, String.valueOf(userId));
    }

}
