package com.creolophus.liuyi.config;

import com.creolophus.liuyi.netty.config.NettyServerConfig;
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
    @Override
    public int getListenPort() {
        return port;
    }

    public int getSocketPort(){
        return port;
    }

    public int getHttpPort(){
        return httpPort;
    }

    @Override
    public String getListenIp() {
        return "127.0.0.1";
    }
}
