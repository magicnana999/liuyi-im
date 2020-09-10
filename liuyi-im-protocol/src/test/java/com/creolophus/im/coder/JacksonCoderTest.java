package com.creolophus.im.coder;

import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class JacksonCoderTest extends CoderTest {

    private JacksonCoder gsonCoder = new JacksonCoder();

    @Test
    public void foo() {
        super.foo();
    }

    @Override
    protected MessageCoder getMessageCode() {
        return gsonCoder;
    }
}