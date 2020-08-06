package com.creolophus.im.processor;

import com.creolophus.im.domain.UserClient;
import com.creolophus.im.protocol.LoginDown;
import com.creolophus.im.protocol.LoginUp;
import com.creolophus.im.protocol.PushMessageDown;
import com.creolophus.im.protocol.PushMessageUp;

/**
 * @author magicnana
 * @date 2020/7/14 下午6:11
 */
public interface UserClientProcessor {

    LoginDown login(LoginUp loginUp);

    PushMessageDown pushMessage(PushMessageDown pushMessageDown);

    void pushMessageAck(PushMessageUp pushMessageUp);

    void registerUserClient(UserClient client);
}
