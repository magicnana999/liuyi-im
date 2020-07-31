package com.creolophus.im.protocol;

/**
* @author magicnana
* @date 2020-01-11
*/

public class LoginOutput {

	private String appKey ;
	private String token ;
	private Long userId;


	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		sb.append("\"appKey\":\"").append(appKey).append('\"');
		sb.append(",\"token\":\"").append(token).append('\"');
		sb.append(",\"userId\":").append(userId);
		sb.append('}');
		return sb.toString();
	}
}
