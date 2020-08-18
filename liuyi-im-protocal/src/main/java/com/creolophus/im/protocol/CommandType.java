package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2019/6/19 上午11:59
 */
public enum CommandType {
    LOGIN(100), SEND_MESSAGE(101), PUSH_MESSAGE(201),
    /**
     * 要不要已读?这是个问题,我先不要了吧!
     */
//    PUSH_ARRIVAL(202),
//    SEND_READ(103),
//    PUSH_READ(203),
    SEND_TYPING(104), PUSH_TYPING(204);

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
