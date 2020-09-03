package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.domain.Command;

/**
 * @author magicnana
 * @date 2020/2/24 下午2:59
 */
public interface RequestProcessor {

    Object processRequest(Command msg);

    void verify(Command msg);
}
