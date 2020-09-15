package com.creolophus.im.config;

import com.creolophus.im.netty.config.NettyServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2020/1/10 下午4:28
 */
@Component
public class GatewayConfig extends NettyServerConfig {


    @Value("${server.socketPort}")
    private int port;

    @Value("${server.port}")
    private int httpPort;

    private String POD_IP = System.getenv("POD_IP");

    public int getHttpPort() {
        return httpPort;
    }

    @Override
    public String getListenIp() {
        return POD_IP;
    }

    @Override
    public int getListenPort() {
        return port;
    }

    public int getSocketPort() {
        return port;
    }
}
