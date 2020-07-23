package com.creolophus.im.service;

import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.protocol.Command;
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
