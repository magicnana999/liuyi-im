package com.creolophus.im.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2019-10-29
 */

public class MessageIdSection extends AbstractEntity {

    @ApiModelProperty(notes = "SectionID")
    @AssignID
    private Long sectionId;
    @ApiModelProperty(notes = "每次增加步长")
    private Integer step;
    @ApiModelProperty(notes = "当前 MessageID")
    private Long currentId;
    @ApiModelProperty(notes = "最大 MessageID")
    private Long maxId;
    private Date createTime;
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Long currentId) {
        this.currentId = currentId;
    }

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
