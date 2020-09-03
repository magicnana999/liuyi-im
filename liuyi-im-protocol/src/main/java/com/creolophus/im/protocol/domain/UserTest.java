package com.creolophus.im.protocol.domain;

/**
 * @author magicnana
 * @date 2020/7/23 下午3:02
 */
public class UserTest {

    public static final String appKey = "1d0d0423ce82ced4851c62c5724265e4";

    public static final 狗男女 张无忌 = new 狗男女(10086100, "e912782f1ee0612220c17214aaeb2892", "张无忌");
    public static final 狗男女 赵敏 = new 狗男女(10086101, "15907024f9ecb4b4a5f088f94d25ce26", "赵敏");
    public static final 狗男女 周芷若 = new 狗男女(10086102, "7be9166d6072cbeec51af0b618896fe3", "周芷若");
    public static final 狗男女 小昭 = new 狗男女(10086103, "054449cf09cdc407f558e2d64d4df595", "小昭");

    public static 狗男女 valueOf(long userId) {
        if(userId == 张无忌.userId) {
            return 张无忌;
        }

        if(userId == 周芷若.userId) {
            return 周芷若;
        }

        if(userId == 赵敏.userId) {
            return 赵敏;
        }

        if(userId == 小昭.userId) {
            return 小昭;
        }

        return null;
    }


    public static class 狗男女 {
        public long userId;
        public String token;
        public String name;

        public 狗男女(long userId, String token, String name) {
            this.userId = userId;
            this.token = token;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
