package com.creolophus.liuyi.netty.serializer;

import com.creolophus.liuyi.netty.protocol.Command;

import java.util.List;

/**
 * @author magicnana
 * @date 2018/10/16 下午5:18
 */
public interface CommandSerializer {

    <Body> List<Body> bodyFromArray(Object jsonArray, Class<Body> clazz);

    <Body> Body bodyFromObject(Object jsonObject, Class<Body> clazz);

    Command decode(byte[] bytes);

    byte[] encode(Command command);

    String toString(Command command);

    String name();
}
