package com.creolophus.im.sdk;

import com.creolophus.im.protocol.domain.Command;
import com.creolophus.im.protocol.type.PushMessageAck;

/**
 * @author magicnana
 * @date 2020/9/2 11:20 AM
 */
public interface MessageReceiver {
    PushMessageAck receivePushMessage(Command command);

}
