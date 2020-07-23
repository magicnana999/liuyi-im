package com.creolophus.im.processor;

import com.creolophus.im.io.LoginInput;
import com.creolophus.im.service.AuthService;
import com.creolophus.im.io.SendMessageInput;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.Auth;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.netty.serializer.CommandSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class RequestProcessorImpl implements RequestProcessor {

    @Resource
    private UserClientProcessor userClientProcessor;

    @Resource
    private CommandSerializer commandSerializer;

    @Resource
    private MessageProcessor messageProcessor;

    @Resource
    private AuthService authService;


    @Override
    public void verify(Command request) {
//        remoteContextValidator.validateCommand(request);
        Auth auth = authService.verify(request.getToken());
        request.setAuth(auth);
//        remoteContextValidator.validateAppKey(request);
//        remoteContextValidator.validateUserId(request);
    }

    @Override
    public Object processRequest(Command request) {
        switch (CommandType.valueOf(request.getHeader().getType())) {
            case CONNECT:
                return userClientProcessor.connect(commandSerializer.bodyFromObject(request.getBody(), LoginInput.class));
            case SEND_MESSAGE:
                return messageProcessor.sendMessage(commandSerializer.bodyFromObject(request.getBody(), SendMessageInput.class));
            default:
                break;
        }

        return null;
    }
}
