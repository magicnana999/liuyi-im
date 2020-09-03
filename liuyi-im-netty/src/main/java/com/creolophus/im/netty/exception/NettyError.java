package com.creolophus.im.netty.exception;

import com.creolophus.im.protocol.domain.Error;
import com.creolophus.liuyi.common.api.ApiError;

/**
 * @author magicnana
 * @date 2020/1/8 下午5:43
 */
public class NettyError extends ApiError implements Error {

    public static final NettyError S_OK = new NettyError(200, "OK");
     private static int error = 1000;
    public static final NettyError E_ERROR = new NettyError(error++, "服务器内部错误");
    public static final NettyError E_TOO_LARGE = new NettyError(error++, "请求体太大");
    public static final NettyError E_RESPONSE_ERROR = new NettyError(error++,"无法响应");
    public static final NettyError E_REQUEST_NO_BODY = new NettyError(error++,"无请求体");
    public static final NettyError E_REQUEST_DECODE_FAIL = new NettyError(error++,"此请求无法解析");
    public static final NettyError E_REQUEST_VALIDATE_ERROR = new NettyError(error++,"格式校验错误,%s");
    public static final NettyError E_REQUEST_AUTH_ERROR = new NettyError(error++,"认证校验错误,%s");

    public NettyError(int code, String message) {
        super(code, message);
    }

    @Override
    public NettyError format(String msg) {
        return new NettyError(this.getCode(), String.format(this.getMessage(),msg));
    }
}
