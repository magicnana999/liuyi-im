package com.creolophus.im.coder;

import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/8/19 4:23 PM
 */
public class GsonCoderTest extends CoderTest {

    private GsonCoder gsonCoder = new GsonCoder();

    @Test
    public void foo() {
        super.foo();
    }

    @Override
    protected MessageCoder getMessageCode() {
        return gsonCoder;
    }
}