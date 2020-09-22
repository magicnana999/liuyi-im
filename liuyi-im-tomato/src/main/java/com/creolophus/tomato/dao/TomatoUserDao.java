package com.creolophus.tomato.dao;

import com.creolophus.tomato.entity.TomatoUser;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Repository
public interface TomatoUserDao extends BaseMapper<TomatoUser> {

}
