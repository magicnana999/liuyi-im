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

public class Group extends AbstractEntity  {

	@ApiModelProperty(notes = "会话 ID")
	@AssignID
	private Long groupId ;
	@ApiModelProperty(notes = "会话 name")
	private String groupName ;
	private Date createTime ;

	public Long getGroupId(){
	    return  groupId;
	}
	public void setGroupId(Long groupId ){
	    this.groupId = groupId;
	}

	public String getGroupName(){
	    return  groupName;
	}
	public void setGroupName(String groupName ){
	    this.groupName = groupName;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

}
