package com.creolophus.liuyi.netty.protocol;

/**
 * @author magicnana
 * @date 2019/6/19 上午11:59
 */
public enum CommandType {
    CONNECT(101),
    GET_REMIND(102),
    CONNECT_GET_REMIND(103),
    DISCONNECT(104),
    SEND_MESSAGE(105),
    PUSH_MESSAGE(106),
    ;

    private int value;

    CommandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CommandType valueOf(int value) {

        for (CommandType code : CommandType.values()) {
            if(code.getValue() == value) {
                return code;
            }
        }
        return null;

    }


}
