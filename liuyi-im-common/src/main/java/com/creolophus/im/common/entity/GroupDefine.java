package com.creolophus.im.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-07-03
 */

public class GroupDefine extends AbstractEntity {

    @ApiModelProperty(notes = "会话 ID")
    @AssignID
    private Long groupId;
    @ApiModelProperty(notes = "会话 name")
    private String groupName;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
