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

public class UserDevice extends AbstractEntity  {

	@ApiModelProperty(notes = "UserID")
	@AssignID
	private Long userId ;
	@ApiModelProperty(notes = "设备ID")
	private String deviceId ;
	@ApiModelProperty(notes = "设备名")
	private String deviceLabel ;
	@ApiModelProperty(notes = "SDK名称")
	private String sdkName ;
	@ApiModelProperty(notes = "SDK版本")
	private String sdkVersion ;
	@ApiModelProperty(notes = "创建时间")
	private Date createTime ;
	@ApiModelProperty(notes = "最后连接时间")
	private Date lastConnectTime ;
	@ApiModelProperty(notes = "更新时间")
	private Date updateTime ;

	public Long getUserId(){
	    return  userId;
	}
	public void setUserId(Long userId ){
	    this.userId = userId;
	}

	public String getDeviceId(){
	    return  deviceId;
	}
	public void setDeviceId(String deviceId ){
	    this.deviceId = deviceId;
	}

	public String getDeviceLabel(){
	    return  deviceLabel;
	}
	public void setDeviceLabel(String deviceLabel ){
	    this.deviceLabel = deviceLabel;
	}

	public String getSdkName(){
	    return  sdkName;
	}
	public void setSdkName(String sdkName ){
	    this.sdkName = sdkName;
	}

	public String getSdkVersion(){
	    return  sdkVersion;
	}
	public void setSdkVersion(String sdkVersion ){
	    this.sdkVersion = sdkVersion;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

	public Date getLastConnectTime(){
	    return  lastConnectTime;
	}
	public void setLastConnectTime(Date lastConnectTime ){
	    this.lastConnectTime = lastConnectTime;
	}

	public Date getUpdateTime(){
	    return  updateTime;
	}
	public void setUpdateTime(Date updateTime ){
	    this.updateTime = updateTime;
	}

}
