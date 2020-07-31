package com.creolophus.im.sdk;

import com.creolophus.im.protocol.Command;

/**
 * @author magicnana
 * @date 2020/7/29 下午6:30
 */
public interface PushProcessor {
    Command receivePush(Command command);
}
