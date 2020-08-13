package com.creolophus.im.netty.utils;

import org.springframework.cloud.sleuth.util.SpanNameUtil;

/**
 * @author magicnana
 * @date 2019/9/18 下午4:06
 */
public enum OS {

    WINDOWS, LINUX, MAC, OTHER;

    public static boolean isLinux() {
        return LINUX.equals(valueOf());
    }

    public static void main(String[] args) {
        System.out.println(SpanNameUtil.toLowerHyphen("111"));
    }

    public static OS valueOf() {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.indexOf("linux") >= 0) {
            return LINUX;
        } else if(osName.indexOf("windows") >= 0) {
            return WINDOWS;
        } else if(osName.indexOf("mac") >= 0) {
            return MAC;
        } else {
            return OTHER;
        }
    }
}
