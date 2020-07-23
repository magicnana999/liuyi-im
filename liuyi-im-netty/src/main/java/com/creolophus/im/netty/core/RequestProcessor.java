package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.Command;

/**
 * @author magicnana
 * @date 2020/2/24 下午2:59
 */
public interface RequestProcessor {

    void verify(Command msg);

    Object processRequest(Command msg);
}
