package com.creolophus.im.domain;

import com.creolophus.im.protocol.Auth;

/**
 * @author magicnana
 * @date 2020/7/23 下午3:02
 */
public class UserTest {

    public static final String appKey = "1d0d0423ce82ced4851c62c5724265e4";

    public static final 狗男女 张无忌 = new 狗男女(100,"e912782f1ee0612220c17214aaeb2892");
    public static final 狗男女 赵敏 = new 狗男女(101,"15907024f9ecb4b4a5f088f94d25ce26");
    public static final 狗男女 周芷若 = new 狗男女(102,"7be9166d6072cbeec51af0b618896fe3");
    public static final 狗男女 小昭 = new 狗男女(103,"054449cf09cdc407f558e2d64d4df595");

    public static class 狗男女 {
        public long userId;
        public String token;

        public 狗男女(long userId, String token) {
            this.userId = userId;
            this.token = token;
        }
    }
}
