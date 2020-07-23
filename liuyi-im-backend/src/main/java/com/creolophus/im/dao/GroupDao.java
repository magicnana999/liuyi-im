package com.creolophus.im.dao;

import com.creolophus.im.common.entity.Group;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface GroupDao extends BaseMapper<Group> {


    @Sql("select group_id from group order by group_id desc limit 0,1")
    Long selectMaxGroupId();

    @Sql("select user_id from group_member where group_id = ?")
    List<Long> selectMemberUserIdByGroupId(Long groupId);
}
