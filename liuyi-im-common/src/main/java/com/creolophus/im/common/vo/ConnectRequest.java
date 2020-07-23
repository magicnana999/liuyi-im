package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/9/16 上午10:52
 */
public class ConnectRequest extends AbstractEntity {

    private String sdkName;
    private String sdkVersion;
    private String deviceLabel;
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceLabel() {
        return deviceLabel;
    }

    public void setDeviceLabel(String deviceLabel) {
        this.deviceLabel = deviceLabel;
    }

    public String getSdkName() {
        return sdkName;
    }

    public void setSdkName(String sdkName) {
        this.sdkName = sdkName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }
}
