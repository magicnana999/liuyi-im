package com.creolophus.im.processor;

import com.creolophus.im.netty.core.ResponseProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.Decoder;
import com.creolophus.im.protocol.PushMessageUp;
import com.creolophus.im.service.UserClientService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class ResponseProcessorImpl implements ResponseProcessor {

    @Resource
    private UserClientService userClientService;

    @Resource
    private Decoder decoder;

    @Override
    public void processResponse(Command response) {
        switch (CommandType.valueOf(response.getHeader().getType())) {
            case PUSH_MESSAGE:
                userClientService.pushMessageAck(decoder.decode(response.getBody(), PushMessageUp.class));
                break;
            default:
                break;
        }
    }
}
