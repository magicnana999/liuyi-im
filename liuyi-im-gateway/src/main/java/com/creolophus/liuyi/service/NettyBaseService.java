package com.creolophus.liuyi.service;

import com.creolophus.liuyi.common.base.BaseService;
import com.creolophus.liuyi.netty.core.ContextProcessor;
import com.creolophus.liuyi.netty.protocol.Command;
import io.netty.channel.Channel;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/16 上午10:55
 */
public class NettyBaseService extends BaseService {

    @Resource
    private ContextProcessor contextProcessor;

    protected String getAppKey() {
        return contextProcessor.getAppKey();
    }

    protected Channel getChannel() {
        return contextProcessor.getChannel();
    }

    protected Command getRequest() {
        return contextProcessor.getRequest();
    }

    protected Command getResponse() {
        return contextProcessor.getResponse();
    }

    protected String getToken() {
        return contextProcessor.getToken();
    }

    protected long getUserId() {
        return contextProcessor.getUserId();
    }

}
