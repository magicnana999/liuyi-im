package com.creolophus.im.processor;

import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.netty.core.RequestProcessor;
import com.creolophus.im.protocol.Auth;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.service.AuthService;
import com.creolophus.im.service.MessageService;
import com.creolophus.im.service.UserClientService;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.PushMessageAck;
import com.creolophus.im.type.SendMessageMsg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/9/12 上午11:27
 */
@Component
public class RequestProcessorImpl implements RequestProcessor {

    @Resource
    private MessageCoder messageCoder;

    @Resource
    private MessageService messageService;

    @Resource
    private UserClientService userClientService;

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
            case LOGIN:
                return userClientService.login(messageCoder.decode(request.getBody(), LoginMsg.class));
            case SEND_MESSAGE:
                return messageService.sendMessage(messageCoder.decode(request.getBody(), SendMessageMsg.class));
            case PUSH_MESSAGE:
                userClientService.pushMessageAck(messageCoder.decode(request.getBody(), PushMessageAck.class));
            default:
                break;
        }
        return null;
    }
}
