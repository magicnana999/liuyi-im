package com.creolophus.liuyi.processor;

import com.creolophus.liuyi.io.SendMessageInput;

/**
 * @author magicnana
 * @date 2020/7/17 下午4:26
 */
public interface MessageProcessor {
    Long sendMessage(SendMessageInput bodyFromObject);
}
