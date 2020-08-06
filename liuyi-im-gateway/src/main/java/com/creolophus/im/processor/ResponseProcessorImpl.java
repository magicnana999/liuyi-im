package com.creolophus.im.processor;

import com.creolophus.im.netty.core.ResponseProcessor;
import com.creolophus.im.netty.serializer.CommandSerializer;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.PushMessageUp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class ResponseProcessorImpl implements ResponseProcessor {

    @Resource
    private UserClientProcessor userClientProcessor;

    @Resource
    private CommandSerializer commandSerializer;

    @Override
    public void processResponse(Command response) {
        switch (CommandType.valueOf(response.getHeader().getType())) {
            case PUSH_MESSAGE:
                userClientProcessor.pushMessageAck(commandSerializer.bodyFromObject(response.getBody(), PushMessageUp.class));
                break;
            default:
                break;
        }
    }
}
