package com.creolophus.im.dao;

import com.creolophus.im.common.entity.GroupDefine;
import com.creolophus.im.common.entity.GroupMember;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface GroupDao extends BaseMapper<GroupDefine> {


    @Sql("select g.* from group_define g,(select group_id from group_member where user_id = ?) mg where g.group_id = mg.group_id order by g.create_time desc "
            + "limit ?,?")
    List<GroupDefine> queryGroupListByMemberId(Long userId, Integer start, Integer end);

    @Sql("select * from group_member where group_id = ? order by create_time asc limit ?,?")
    List<GroupMember> queryGroupMemberListByGroupId(Long groupId, Integer start, Integer end);

    @Sql("select group_id from group order by group_id desc limit 0,1")
    Long selectMaxGroupId();

    @Sql("select user_id from group_member where group_id = ?")
    List<Long> selectMemberUserIdByGroupId(Long groupId);
}
