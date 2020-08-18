package com.creolophus.im.netty.utils;

/**
 * @author magicnana
 * @date 2020/8/14 2:54 PM
 */
public class Badge {
    public static enum OPER{
        CONNECT("连接建立"),
        CLOSE("连接关闭"),
        LOGIN("用户登录"),
        SEND_MESSAGE("消息发送"),
        PUSH_MESSAGE("消息推送"),
        ;

        private String value;

        OPER(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static enum RET{
        OK("成功"),
        FAIL("失败"),
        ;
        private String value;

        RET(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
