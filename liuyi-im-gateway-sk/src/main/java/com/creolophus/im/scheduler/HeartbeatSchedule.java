package com.creolophus.im.scheduler;

import com.creolophus.im.config.GatewayConfig;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.liuyi.common.logger.Entry;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/6/29 下午4:17
 */
@Service
public class HeartbeatSchedule {

    @Resource
    private GatewayConfig gatewayConfig;

    @Resource
    private BackendFeign backendFeign;


    @Entry
//    @Scheduled(fixedRate = 1000 * 60)
    public void heartbeat() {
        backendFeign.registerGateway(gatewayConfig.getListenIp(), gatewayConfig.getHttpPort());
    }
}
