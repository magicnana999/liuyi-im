package com.creolophus.im.common.entity;

import com.creolophus.liuyi.common.base.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;


/**
 * @author magicnana
 * @date 2020-07-03
 */

public class User extends AbstractEntity {

    @AssignID
    private Long userId;
    @ApiModelProperty(notes = "1:有效,0:无效")
    private Integer state;
    private String appKey;
    private String name;
    private Long outId;
    @ApiModelProperty(notes = "头像")
    private String portrait;
    private Date createTime;
    private Integer userType;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public enum UserType {
        USER(1), SYSTEM(2);

        int value;

        UserType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static UserType valueOf(Integer value) {
            if(value == null) {
                return null;
            }
            for (UserType userType : UserType.values()) {
                if(userType.value == value) {
                    return userType;
                }
            }
            return null;
        }

    }
}
