package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2020/8/19 4:33 PM
 */
public enum MessageType {
    SINGLE(1), GROUP(2);

    int value;

    MessageType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static MessageType valueOf(Integer value) {
        if(value == null) {
            return null;
        }

        for (MessageType mt : MessageType.values()) {
            if(mt.value() == value) {
                return mt;
            }
        }

        return null;
    }
}
