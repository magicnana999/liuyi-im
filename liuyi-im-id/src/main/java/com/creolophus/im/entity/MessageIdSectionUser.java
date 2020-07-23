package com.creolophus.im.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;


/**
 * @author magicnana
 * @date 2019-10-29
 */

public class MessageIdSectionUser extends AbstractEntity {

    @ApiModelProperty(notes = "UserId")
    @AssignID
    private Long userId;
    @ApiModelProperty(notes = "SectionID")
    private Long sectionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

}
