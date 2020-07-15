package com.creolophus.liuyi.storage;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.common.base.BaseStorage;
import com.creolophus.liuyi.common.entity.Message;
import com.creolophus.liuyi.common.redis.RedisClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Service
public class MessageStorage extends BaseStorage {

    private static final String MESSAGE_LIST = PREFIX + "message_list";

    @Resource
    private RedisClient redisClient;

    public Long asyncMessage(Message message) {
        if(message == null) return 0L;
        return redisClient.rpush(getMessageListKey(), JSON.toJSONString(message));
    }

    public Long getMaxMessageId(Long receiverId) {
        List<String> list = redisClient.lrange(getMessageListKey(), 0, -1);
        for (String json : list) {
            Message message = JSON.parseObject(json, Message.class);
            if(message.getReceiverId().equals(receiverId)) {
                return message.getMessageId();
            }
        }
        return null;
    }

    private String getMessageListKey() {
        return MESSAGE_LIST;
    }

    public List<String> popAsyncMessage() {
        List<String> json = redisClient.blpop(0, getMessageListKey());
        return json;
    }
}