package com.creolophus.im.netty.exception;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 * @author magicnana
 * @date 2019/9/18 上午10:38
 */
public class NettyTooMuchRequestException extends NettyException {

    public NettyTooMuchRequestException(String message) {
        super(message);
    }

    public NettyTooMuchRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
