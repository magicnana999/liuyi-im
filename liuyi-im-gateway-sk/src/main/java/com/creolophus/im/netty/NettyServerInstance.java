package com.creolophus.im.netty;

import com.creolophus.im.netty.config.NettyServerConfig;
import com.creolophus.im.netty.core.*;
import com.creolophus.im.scheduler.HeartbeatSchedule;
import com.creolophus.liuyi.common.logger.TracerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2018/10/8 下午4:26
 */
@Component
public class NettyServerInstance extends AbstractNettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerInstance.class);

    @Value("${server.port}")
    private int port;

    @Resource
    private HeartbeatSchedule heartbeatSchedule;

    public NettyServerInstance(
            NettyServerConfig nettyServerConfig,
            TracerUtil tracerUtil,
            ContextProcessor contextProcessor,
            RequestProcessor requestProcessor,
            NettyServerChannelEventListener nettyServerChannelEventListener,
            ResponseProcessor responseProcessor) {
        super(nettyServerConfig, tracerUtil, contextProcessor, requestProcessor, nettyServerChannelEventListener, responseProcessor);
    }

    @Override
    public void shutdown() {
        this.shutdown();
    }

    @Override
    public void start() {
        heartbeatSchedule.heartbeat();
        super.start();
    }

}
