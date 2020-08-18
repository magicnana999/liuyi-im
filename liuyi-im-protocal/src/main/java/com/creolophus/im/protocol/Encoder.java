package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2020/8/17 6:20 PM
 */
public interface Encoder {
    byte[] encode(Command nettyCommand);
}
