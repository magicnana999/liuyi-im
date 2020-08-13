package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/10/31 下午2:33
 */
public class DisconnectResponse extends AbstractEntity {

    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
