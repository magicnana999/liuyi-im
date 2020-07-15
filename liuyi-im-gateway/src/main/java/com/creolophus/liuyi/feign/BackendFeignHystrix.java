package com.creolophus.liuyi.feign;

import com.creolophus.liuyi.common.security.UserSecurity;

/**
 * @author magicnana
 * @date 2019/7/2 上午12:46
 */
public class BackendFeignHystrix implements BackendFeign {


    @Override
    public UserSecurity verifyToken(String token) {
        return null;
    }

    @Override
    public void registerGateway(String ip, Integer port) {

    }

    @Override
    public void registerUserClient(String gatewayIp, Integer gatewayPort, Long userId) {

    }

    @Override
    public void unregisterGateway(String ip, Integer port) {

    }

    @Override
    public void unregisterUserClient(String gatewayIp, Integer gatewayPort, Long userId) {

    }

    @Override
    public Long sendMessage(Long senderId, Integer messageType, Long targetId, String sendTime, String messageBod) {
        return null;
    }
}
