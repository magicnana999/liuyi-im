package com.creolophus.im.netty.exception;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 * @author magicnana
 * @date 2019/9/18 上午10:38
 */
public class NettyCommandException extends NettyException {

    private NettyError nettyError;

    public NettyCommandException(NettyError nettyError) {
        super(nettyError.getMessage());
    }

    public NettyCommandException(NettyError nettyError, Throwable cause) {
        super(nettyError.getMessage(), cause);
    }

    public NettyError getNettyError() {
        return nettyError;
    }
}
