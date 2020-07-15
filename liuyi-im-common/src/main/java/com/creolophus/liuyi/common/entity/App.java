package com.creolophus.liuyi.common.entity;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;
import org.beetl.sql.core.annotatoin.Table;


import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.AssignID;
import com.creolophus.liuyi.common.base.AbstractEntity;


/**
* @author magicnana
* @date 2020-07-03
*/

public class App extends AbstractEntity  {

	@AutoID
	private Long appId ;
	@ApiModelProperty(notes = "1:有效,0:无效")
	private Integer state ;
	private String appKey ;
	private String appName ;
	private Date createTime ;

	public Long getAppId(){
	    return  appId;
	}
	public void setAppId(Long appId ){
	    this.appId = appId;
	}

	public Integer getState(){
	    return  state;
	}
	public void setState(Integer state ){
	    this.state = state;
	}

	public String getAppKey(){
	    return  appKey;
	}
	public void setAppKey(String appKey ){
	    this.appKey = appKey;
	}

	public String getAppName(){
	    return  appName;
	}
	public void setAppName(String appName ){
	    this.appName = appName;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

}
