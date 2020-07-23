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

public class GroupMember extends AbstractEntity  {

	@ApiModelProperty(notes = "关联会话ID")
	@AssignID
	private Long groupId ;
	@ApiModelProperty(notes = "会话持有人 ID")
	@AssignID
	private Long userId ;
	private String memberName ;
	private Date createTime ;

	public Long getGroupId(){
	    return  groupId;
	}
	public void setGroupId(Long groupId ){
	    this.groupId = groupId;
	}

	public Long getUserId(){
	    return  userId;
	}
	public void setUserId(Long userId ){
	    this.userId = userId;
	}

	public String getMemberName(){
	    return  memberName;
	}
	public void setMemberName(String memberName ){
	    this.memberName = memberName;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

}
