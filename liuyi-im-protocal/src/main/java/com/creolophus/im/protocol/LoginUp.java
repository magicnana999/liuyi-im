package com.creolophus.im.protocol;

/**
 * client -> server
* @author magicnana
* @date 2020-01-11
*/

public class LoginUp {

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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"deviceLabel\":\"").append(deviceLabel).append('\"');
		sb.append(",\"sdkName\":\"").append(sdkName).append('\"');
		sb.append(",\"sdkVersion\":\"").append(sdkVersion).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
