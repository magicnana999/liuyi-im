package com.creolophus.im.internal;

import com.creolophus.im.protocol.MessageKind;
import com.creolophus.liuyi.common.json.GsonUtil;

/**
 * @author magicnana
 * @date 2020/9/10 4:02 PM
 */
public class MessageBody {

    public static  <T> T toJava(Integer messageKind,String messageBody){
        switch (MessageKind.valueOf(messageKind)) {
            case TEXT:
                return GsonUtil.toJava(messageBody, MessageText.class);
            case URL:
                return GsonUtil.toJava(messageBody, MessageUrl.class);
            case IMAGE:
                return GsonUtil.toJava(messageBody, MessageImage.class);
            case AUDIO:
                return GsonUtil.toJava(messageBody, MessageAudio.class);
            case VIDEO:
                return GsonUtil.toJava(messageBody, MessageVideo.class);
            default:
                return null;
        }
    }

    public String toJson(){
        return GsonUtil.toJson(this);
    }

}
