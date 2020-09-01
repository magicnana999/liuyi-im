package com.creolophus.im.netty.core;

import com.creolophus.im.common.api.LiuYiApiContextValidator;
import com.creolophus.im.netty.exception.NettyCommandWithResException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.liuyi.common.api.MdcUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author magicnana
 * @date 2020/7/23 下午3:35
 */
public abstract class AbstractContextProcessor extends LiuYiApiContextValidator implements ContextProcessor {

    protected void throwError(String commandSequence,int commandType,NettyError remoteError) {
        Command response = Command.newAck(commandSequence, commandType, remoteError);
        throw new NettyCommandWithResException(response);
    }

    @Override
    public void validateCommand(Command command) {
        if(command == null){
            throwError("0",0, NettyError.E_REQUEST_DECODE_FAIL);
        }

        if(command.getHeader()==null){
            throwError("0", 0,NettyError.E_REQUEST_VALIDATE_ERROR.format("没有 Header"));
        }

        if(StringUtils.isBlank(command.getHeader().getSeq())){
            throwError(command.getHeader().getSeq(),command.getHeader().getType(), NettyError.E_REQUEST_VALIDATE_ERROR.format("没有 Sequence"));
        }

        if(CommandType.valueOf(command.getHeader().getType()) == null){
            throwError(command.getHeader().getSeq(),command.getHeader().getType() , NettyError.E_REQUEST_VALIDATE_ERROR.format("没有这样的 Type"));
        }

        if(StringUtils.isBlank(command.getToken())){
            throwError(command.getHeader().getSeq(),command.getHeader().getType(),NettyError.E_REQUEST_VALIDATE_ERROR.format("没有 Token"));
        }
    }

    @Override
    public void validateAfterVerify(Command request) {

        if(request.getAuth()==null){
            throwError(request.getHeader().getSeq(),request.getHeader().getType(),NettyError.E_REQUEST_AUTH_ERROR.format("没有 Auth"));
        }

        if(StringUtils.isBlank(request.getAuth().getAppKey())) {
            throwError(request.getHeader().getSeq(),request.getHeader().getType(),NettyError.E_REQUEST_AUTH_ERROR.format("没有 AppKey"));
        }

        if(request.getAuth().getUserId()==null || request.getAuth().getUserId()==0){
            throwError(request.getHeader().getSeq(),request.getHeader().getType(),NettyError.E_REQUEST_AUTH_ERROR.format("没有 UserID"));
        }

        setUserId(request.getAuth().getUserId());
        setToken(request.getToken());
        setAppKey(request.getAuth().getAppKey());
        MdcUtil.setExt(""+request.getAuth().getUserId());
    }
}
