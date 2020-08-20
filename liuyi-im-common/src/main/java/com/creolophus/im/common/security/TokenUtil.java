package com.creolophus.im.common.security;

import com.creolophus.liuyi.common.codec.MD5Util;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author magicnana
 * @date 2019/6/5 下午5:25
 */
public class TokenUtil {

    private static String GLOBAL_SECRET = "4b48b56e2c8557cdcdd94241e923db58";

    public static String token(Long userId) {
        return MD5Util.md5Hex(GLOBAL_SECRET + String.valueOf(userId) + System.currentTimeMillis() + Thread.currentThread()
                .getName() + RandomUtils.nextInt(0, Integer.MAX_VALUE));
    }
}
