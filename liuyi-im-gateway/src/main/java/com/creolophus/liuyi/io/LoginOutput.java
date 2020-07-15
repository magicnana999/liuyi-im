package com.creolophus.liuyi.io;

import com.creolophus.liuyi.common.base.AbstractVo;


/**
* @author magicnana
* @date 2020-01-11
*/

public class LoginOutput extends AbstractVo {

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
}
