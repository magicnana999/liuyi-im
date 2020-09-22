package com.creolophus.tomato.vo;

import com.creolophus.tomato.base.BaseVo;

/**
 * @author magicnana
 * @date 2020/9/9 9:24 AM
 */
public class UserSimpleVo extends BaseVo {

    private String name;
    private String phone;
    private String portrait;

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
}
