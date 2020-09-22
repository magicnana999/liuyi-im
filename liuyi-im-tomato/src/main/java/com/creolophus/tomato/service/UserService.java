package com.creolophus.tomato.service;

import com.creolophus.tomato.base.BaseService;
import com.creolophus.tomato.dao.TomatoUserDao;
import com.creolophus.tomato.entity.TomatoUser;
import com.creolophus.tomato.storage.UserStorage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class UserService extends BaseService {

    @Resource
    private TomatoUserDao tomatoUserDao;

    @Resource
    private UserStorage userStorage;

    public TomatoUser findUserByPhone(String phone) {
        TomatoUser tomatoUser = userStorage.getUserInfo(phone);
        if(tomatoUser == null) {
            if(userStorage.lockUserInfo(phone)) {
                try {
                    tomatoUser = selectUserByPhone(phone);
                    if(tomatoUser != null) {
                        userStorage.setUserInfo(tomatoUser);
                    }
                } finally {
                    userStorage.unlockUserInfo(phone);
                }
            }
        }
        return tomatoUser;
    }

    public TomatoUser findUserByUserId(Long userId) {
        TomatoUser tomatoUser = userStorage.getUserInstance(userId);
        if(tomatoUser == null) {
            if(userStorage.lockUserInstance(userId)) {
                try {
                    tomatoUser = selectUserByUserId(userId);
                    if(tomatoUser != null) {
                        userStorage.setUserInstance(tomatoUser);
                    }
                } finally {
                    userStorage.unlockUserInstance(userId);
                }
            }
        }
        return tomatoUser;
    }

    private TomatoUser selectUserByPhone(String phone) {
        return tomatoUserDao.createLambdaQuery().andEq(TomatoUser::getPhone, phone).single();
    }

    private TomatoUser selectUserByUserId(Long userId) {
        return tomatoUserDao.single(userId);
    }

}
