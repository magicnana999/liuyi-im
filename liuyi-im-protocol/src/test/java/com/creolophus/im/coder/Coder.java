package com.creolophus.im.coder;

import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.MessageType;
import com.creolophus.im.type.*;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class Coder {
    protected Command loginMsg;
    protected Command loginAck;

    protected Command sendMessageMsg;
    protected Command sendMessageAck;

    protected Command pushMessageMsg;
    protected Command pushMessageAck;

    public Coder(){

        String token = "token";

        loginMsg = Command.newMsg(CommandType.LOGIN.value(), new LoginMsg("JUnitDevice","JUnitSdk","1.0.0")).withToken(token);
        loginAck = Command.newAck(loginMsg.getHeader().getSeq(), loginMsg.getHeader().getType(), new LoginAck("JUnitAppKey","JUnitToken",100L));

        sendMessageMsg = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), 100L, "Hi")).withToken(token);
        sendMessageAck = Command.newAck(sendMessageMsg.getHeader().getSeq(), sendMessageMsg.getHeader().getType(), new SendMessageAck(1L));

        pushMessageMsg = Command.newMsg(CommandType.PUSH_MESSAGE.value(), new PushMessageMsg(1L,MessageType.SINGLE.value(),200L,"Hi",100L,102L));
        pushMessageAck = Command.newAck(pushMessageMsg.getHeader().getSeq(), pushMessageMsg.getHeader().getType(), new PushMessageAck(1L,200L,100L,102L)).withToken(token);
    }

}
