package com.creolophus.im.protocol;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/17 6:20 PM
 */
public interface CommandDecoder {

    <T> T decode(Object body, Class<T> clazz);

    Command decode(ByteBuffer byteBuffer);

    Command decode(byte[] byteBuffer);

}
