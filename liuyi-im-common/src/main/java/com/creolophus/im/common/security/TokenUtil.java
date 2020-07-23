package com.creolophus.im.common.security;

import com.creolophus.liuyi.common.codec.MD5Util;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2019/6/5 下午5:25
 */
public class TokenUtil {

    private static String GLOBAL_SECRET = "4b48b56e2c8557cdcdd94241e923db58";

    @Test
    public void generic() {
        System.out.println(TokenUtil.token(1123435354343L));
    }

    public static String token(Long userId) {
        return MD5Util.md5Hex(GLOBAL_SECRET + String.valueOf(userId) + System.currentTimeMillis() + RandomUtils.nextInt(0, Integer.MAX_VALUE));
    }
}
