package com.creolophus.im.coder;

import com.creolophus.im.internal.*;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.MessageKind;
import com.creolophus.im.protocol.MessageType;
import com.creolophus.im.type.LoginAck;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.SendMessageAck;
import com.creolophus.im.type.SendMessageMsg;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class Coder {
    protected Command loginMsg;
    protected Command loginAck;

    protected Command sendMessageMsgText;
    protected Command sendMessageMsgUrl;
    protected Command sendMessageMsgImage;
    protected Command sendMessageMsgAudio;
    protected Command sendMessageMsgVideo;
    protected Command sendMessageAck;

    public Coder() {

        String token = "token";

        loginMsg = Command.newMsg(CommandType.LOGIN.value(), new LoginMsg("JUnitDevice", "JUnitSdk", "1.0.0")).withToken(token);
        loginAck = Command.newAck(loginMsg.getHeader().getSeq(), loginMsg.getHeader().getType(), new LoginAck("JUnitAppKey", "JUnitToken", 100L));

        sendMessageMsgText = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), MessageKind.TEXT.value(), 100L,
                                                                                                 new MessageText("HelloWorld")))
                .withToken(token);

        sendMessageMsgUrl = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), MessageKind.URL.value(), 100L,
                                                                                                new MessageUrl("http://www.google.com")))
                .withToken(token);

        sendMessageMsgImage = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), MessageKind.IMAGE.value(), 100L
                , new MessageImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599735398814&di=1f484ff43fefb8b92b95ba069dd65be5" +
                                           "&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw512h339%2F20180113%2Fa67c-fyqrewh5382636.jpg", "https" +
                "://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1599735432338&di=95b2f8a56ab6d173e5696c2f605229d2&imgtype=0&src=http%3A%2F" + "%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201709%2F17%2F20170917230908_sHAvE.jpeg", "赵敏")))
                .withToken(token);

        sendMessageMsgAudio = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), MessageKind.AUDIO.value(), 100L
                , new MessageAudio(100, "封面图", "白眉大侠 11", "http://www.baimeidaxia/11")))
                .withToken(token);

        sendMessageMsgVideo = Command.newMsg(CommandType.SEND_MESSAGE.value(), new SendMessageMsg(MessageType.SINGLE.value(), MessageKind.VIDEO.value(), 100L
                , new MessageVideo(100, "封面图", "黑鹰坠落 1080P 熟肉", "http://www.baimeidaxia/11")))
                .withToken(token);


        sendMessageAck = Command.newAck(sendMessageMsgText.getHeader().getSeq(), sendMessageMsgText.getHeader().getType(), new SendMessageAck(1L));

    }

}
