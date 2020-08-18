package com.creolophus.im.processor;

import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.*;
import com.creolophus.im.service.AuthService;
import com.creolophus.im.service.MessageService;
import com.creolophus.im.service.UserClientService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class RequestProcessorImpl implements RequestProcessor {

    @Resource
    private CommandDecoder commandDecoder;

    @Resource
    private MessageService messageService;

    @Resource
    private UserClientService userClientService;

    @Resource
    private AuthService authService;

    @Override
    public Object processRequest(Command request) {
        switch (CommandType.valueOf(request.getHeader().getType())) {
            case LOGIN:
                return userClientService.login(commandDecoder.decode(request.getBody(), LoginUp.class));
            case SEND_MESSAGE:
                return messageService.sendMessage(commandDecoder.decode(request.getBody(), SendMessageUp.class));
            case PUSH_MESSAGE:
                userClientService.pushMessageAck(commandDecoder.decode(request.getBody(), PushMessageUp.class));
            default:
                break;
        }

        return null;
    }

    @Override
    public void verify(Command request) {
//        remoteContextValidator.validateCommand(request);
        Auth auth = authService.verify(request.getToken());
        request.setAuth(auth);
//        remoteContextValidator.validateAppKey(request);
//        remoteContextValidator.validateUserId(request);
    }
}
