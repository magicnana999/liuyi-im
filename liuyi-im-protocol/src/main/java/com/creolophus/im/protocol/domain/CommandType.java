package com.creolophus.im.protocol.domain;

/**
 * @author magicnana
 * @date 2019/6/19 上午11:59
 */
public enum CommandType {
    LOGIN(100), SEND_MESSAGE(101), PUSH_MESSAGE(201),
    ;
    private int value;

    CommandType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static CommandType valueOf(int value) {

        for (CommandType code : CommandType.values()) {
            if(code.value() == value) {
                return code;
            }
        }
        return null;

    }


}
