package com.creolophus.im.netty.exception;

import com.creolophus.liuyi.common.api.ApiError;

/**
 * @author magicnana
 * @date 2020/1/8 下午5:43
 */
public class NettyError extends ApiError {

     private static int error = 1000;


    public static final NettyError S_OK = new NettyError(200, "OK");
    public static final NettyError E_ERROR = new NettyError(error++, "服务器内部错误");
    public static final NettyError E_TOO_LARGE = new NettyError(error++, "请求体太大");
    public static final NettyError E_RESPONSE_ERROR = new NettyError(error++,"无法响应");
    public static final NettyError E_REQUEST_BODY_NULL = new NettyError(error++,"无请求体");
    public static final NettyError E_REQUEST_BODY_DECODE_ERROR = new NettyError(error++,"请求体格式错误");
    public static final NettyError E_REQUEST_BODY_VALIDATE_ERROR = new NettyError(error++,"请求体格式错误,%s");
    public static final NettyError E_REQUEST_BODY_AUTH_ERROR = new NettyError(error++,"请求体无法认证");

    public NettyError(int code, String message) {
        super(code, message);
    }

    @Override
    public NettyError format(String msg) {
        return new NettyError(this.getCode(), String.format(this.getMessage(),msg));
    }
}
