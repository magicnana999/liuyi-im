package com.creolophus.im.dao;

import com.creolophus.im.common.entity.Message;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface MessageDao extends BaseMapper<Message> {


    @Sql("select message_id from message where receiver_id = ? order by message_id desc limit 0,1")
    Long selectMaxMessageId(Long receiverId);

    @Sql("select * from message where receiver_id=? and message_id >?")
    List<Message> selectMessageList(Long receiverId, Long messageId);

}
