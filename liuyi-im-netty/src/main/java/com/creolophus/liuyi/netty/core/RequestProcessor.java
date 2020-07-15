package com.creolophus.liuyi.netty.core;

import com.creolophus.liuyi.netty.protocol.Command;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author magicnana
 * @date 2020/2/24 下午2:59
 */
public interface RequestProcessor {

    void verify(Command msg);

    Object processRequest(Command msg);
}
