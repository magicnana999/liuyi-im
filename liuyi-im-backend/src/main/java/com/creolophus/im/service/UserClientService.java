package com.creolophus.im.service;

import com.creolophus.im.domain.GatewayAddr;
import com.creolophus.im.storage.GatewayStorage;
import com.creolophus.im.common.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/6/20 下午6:27
 */
@Service
public class UserClientService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(UserClientService.class);

    @Resource
    private GatewayStorage gatewayStorage;

    public void registerUserClient(String gatewayIp,Integer gatewayPort,Long userId) {
        gatewayStorage.registerUserClient(gatewayIp,gatewayPort,userId);
    }

    public void unregisterUserClient(String gatewayIp,Integer gatewayPort,Long userId) {
        gatewayStorage.unregisterUserClient(gatewayIp,gatewayPort,userId);
    }

    public GatewayAddr findUserClient(Long userId){
        return gatewayStorage.getUserClient(userId);
    }
}