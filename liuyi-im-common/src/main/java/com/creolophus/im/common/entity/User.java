package com.creolophus.im.common.entity;
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

public class User extends AbstractEntity  {

	@AssignID
	private Long userId ;
	@ApiModelProperty(notes = "1:有效,0:无效")
	private Integer state ;
	private String appKey ;
	private String name ;
	private Long outerId ;
	@ApiModelProperty(notes = "头像")
	private String portrait ;
	private Date createTime ;

	public Long getUserId(){
	    return  userId;
	}
	public void setUserId(Long userId ){
	    this.userId = userId;
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

	public String getName(){
	    return  name;
	}
	public void setName(String name ){
	    this.name = name;
	}

	public Long getOuterId(){
	    return  outerId;
	}
	public void setOuterId(Long outerId ){
	    this.outerId = outerId;
	}

	public String getPortrait(){
	    return  portrait;
	}
	public void setPortrait(String portrait ){
	    this.portrait = portrait;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

}
