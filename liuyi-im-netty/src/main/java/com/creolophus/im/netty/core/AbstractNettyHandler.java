package com.creolophus.im.netty.core;

import com.creolophus.im.netty.exception.NettyCommandException;
import com.creolophus.im.netty.exception.NettyCommandWithResException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.protocol.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 2020/8/5 下午3:45
 */
public abstract class AbstractNettyHandler extends SimpleChannelInboundHandler<Command> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyHandler.class);

    protected abstract RequestProcessor getRequestProcessor();

    public Command handleExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//            remoteContextValidator.begin("exceptionCaught");
        logger.error("{} {} {}", ctx.channel(), cause.getClass().getSimpleName(), cause.getMessage(), cause);

        Command response = null;

        if(cause!=null && cause instanceof NettyCommandWithResException){
            NettyCommandWithResException e = (NettyCommandWithResException)cause;
            response = e.getResponse();
        } else if(cause!=null && cause instanceof NettyCommandException){
            NettyCommandException e = (NettyCommandException)cause;
            response = Command.newAck(
//                        RemoteContext.getContext().getRequest().getOpaque(),
//                        RemoteContext.getContext().getRequest().getHeader().getCode(),
                    "100", 12, e.getNettyError());
        } else if(cause!= null && cause instanceof DecoderException){
            response = Command.newAck("0", 0, NettyError.E_REQUEST_DECODE_FAIL);
        } else{
            response = Command.newAck(
//                        RemoteContext.getContext().getRequest().getOpaque(),
//                        RemoteContext.getContext().getRequest().getHeader().getCode(),
                    "100", 12, NettyError.E_ERROR);
        }

        return response;
    }

    public Command handleRequest(Command cmd) {
        Command response;
        try {
            final Object requestReturn = getRequestProcessor().processRequest(cmd);

            if(requestReturn == null) {
                return null;
            }
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), requestReturn);
        } catch (NettyCommandWithResException e) {
            logger.error(e.getMessage(), e);
            response = e.getResponse();
        } catch (NettyCommandException e) {
            logger.error(e.getMessage(), e);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), e.getNettyError());
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), NettyError.E_ERROR);
        }
        return response;
    }

}
