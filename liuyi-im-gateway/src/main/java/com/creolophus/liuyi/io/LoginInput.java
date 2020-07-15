package com.creolophus.liuyi.io;

import com.creolophus.liuyi.common.base.AbstractEntity;
import com.creolophus.liuyi.common.base.AbstractVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
* @author magicnana
* @date 2020-01-11
*/

public class LoginInput extends AbstractVo {

	private String deviceLabel ;
	private String sdkName ;
	private String sdkVersion ;


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
