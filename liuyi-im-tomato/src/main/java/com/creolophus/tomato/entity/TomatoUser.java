package com.creolophus.tomato.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-09-08
 */

public class TomatoUser extends AbstractEntity {

    @AssignID
    private Long userId;
    @ApiModelProperty(notes = "1:有效,0:无效")
    private Integer state;
    private Long imId;
    private String name;
    private String phone;
    @ApiModelProperty(notes = "头像")
    private String portrait;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getImId() {
        return imId;
    }

    public void setImId(Long imId) {
        this.imId = imId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"userId\":").append(userId);
        sb.append(",\"state\":").append(state);
        sb.append(",\"imId\":").append(imId);
        sb.append(",\"name\":\"").append(name).append('\"');
        sb.append(",\"phone\":\"").append(phone).append('\"');
        sb.append(",\"portrait\":\"").append(portrait).append('\"');
        sb.append(",\"createTime\":\"").append(createTime).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
