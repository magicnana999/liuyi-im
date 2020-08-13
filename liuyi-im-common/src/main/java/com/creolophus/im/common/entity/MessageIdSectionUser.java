package com.creolophus.im.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import org.beetl.sql.core.annotatoin.AssignID;


/**
 * @author magicnana
 * @date 2020-07-03
 */

public class MessageIdSectionUser extends AbstractEntity {

    @AssignID
    private Long userId;
    private Long sectionId;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
