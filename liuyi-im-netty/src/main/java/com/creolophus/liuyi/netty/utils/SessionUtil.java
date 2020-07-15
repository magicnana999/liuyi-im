package com.creolophus.liuyi.netty.utils;

import javax.websocket.Session;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 下午2:06
 */
public class SessionUtil {

    public static String getSessionId(Session session){
        return session==null?null:session.getId();
    }

}
