package com.creolophus.im.netty.exception;

import com.creolophus.im.netty.protocol.Command;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 上午10:38
 */
public class NettyCommandWithResException extends NettyException {

    private Command response;

    public NettyCommandWithResException(Command response) {
        super(response.getHeader().getError());
        this.response = response;
    }

    public NettyCommandWithResException(Command response, Throwable cause) {
        super(response.getHeader().getError(), cause);
        this.response = response;
    }

    public Command getResponse() {
        return response;
    }
}
