package com.creolophus.im.processor;

import com.creolophus.im.protocol.SendMessageDown;
import com.creolophus.im.protocol.SendMessageUp;

/**
 * @author magicnana
 * @date 2020/7/17 下午4:26
 */
public interface MessageProcessor {
    SendMessageDown sendMessage(SendMessageUp bodyFromObject);
}
