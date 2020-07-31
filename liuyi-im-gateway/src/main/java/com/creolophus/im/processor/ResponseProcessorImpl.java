package com.creolophus.im.processor;

import com.creolophus.im.netty.core.ResponseProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import org.springframework.stereotype.Component;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class ResponseProcessorImpl implements ResponseProcessor {

    @Override
    public void processResponse(Command response) {
        switch (CommandType.valueOf(response.getHeader().getType())) {
            case PUSH_MESSAGE:
                break;
            default:
                break;
        }
    }
}
