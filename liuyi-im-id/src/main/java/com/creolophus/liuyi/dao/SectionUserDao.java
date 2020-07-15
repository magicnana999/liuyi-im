package com.creolophus.liuyi.dao;

import com.creolophus.liuyi.entity.MessageIdSectionUser;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface SectionUserDao extends BaseMapper<MessageIdSectionUser> {

    /**
     * 如果表或者 where 筛选后结果集为空,则 count 返回 0 <br>
     * 如果表或者 where 筛选后结果集为空并且有 group by,则 count 返回 NULL<br>
     *
     * @param sectionId
     * @return
     */
    @Sql(value = "select count(1) as size from message_id_section_user where section_id=?")
    Integer selectSectionSize(Long sectionId);
}
