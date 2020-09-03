package com.creolophus.im.protocol.coder;

/**
 * @author magicnana
 * @date 2020/9/2 11:11 AM
 */
public enum MessageCoderSelector {
    JACKSON("jackson",new JacksonCoder()),
    GSON("gson",new GsonCoder()),
    PROTOBUF("protobuf",new ProtoCoder()),
    ;


    private String label;
    private MessageCoder messageCoder;

    MessageCoderSelector(String label, MessageCoder messageCoder) {
        this.label = label;
        this.messageCoder = messageCoder;
    }

    public String getLabel() {
        return label;
    }

    public MessageCoder getMessageCoder() {
        return messageCoder;
    }
}
