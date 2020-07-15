package com.creolophus.liuyi.processor;

import com.creolophus.liuyi.domain.UserClient;
import com.creolophus.liuyi.io.LoginInput;

/**
 * @author magicnana
 * @date 2020/7/14 下午6:11
 */
public interface UserClientProcessor {

    Object connect(LoginInput bodyFromObject);

    Long pushMessage(Long messageId, Integer messageType, String messageBody, Long receiverId, Long groupId, Long senderId);

    void registerUserClient(UserClient client);
}
