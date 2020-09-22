package com.creolophus.im.config;

import com.creolophus.im.netty.config.NettyServerConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2020/1/10 下午4:28
 */
@Component
public class GatewayConfig extends NettyServerConfig {


    @Value("${server.port}")
    private int port;

    private String POD_IP = StringUtils.isBlank(System.getenv("POD_IP")) ? "127.0.0.1" : System.getenv("POD_IP");

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
