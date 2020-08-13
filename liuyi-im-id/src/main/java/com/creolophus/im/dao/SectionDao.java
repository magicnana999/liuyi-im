package com.creolophus.im.dao;

import com.creolophus.im.entity.MessageIdSection;
import com.creolophus.im.vo.MessageIdSectionLimitVo;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface SectionDao extends BaseMapper<MessageIdSection> {

    @Sql(value = "select uu.size,ss.* from (select s.* from message_id_section s,(select section_id from message_id_section order by section_id desc limit 0," +
            "1) max_section where s.section_id=max_section.section_id) ss left join (select count(1) as size,section_id from message_id_section_user group by" +
            " section_id) uu on ss.section_id=uu.section_id")
    MessageIdSectionLimitVo selectMaxSection();


    @Sql(value = "update message_id_section set current_id=?,max_id=max_id+step where section_id=? and max_id=?")
    Integer updateCurrentAndMax(Long currentId, Long sectionId, Long originMaxId);
}
