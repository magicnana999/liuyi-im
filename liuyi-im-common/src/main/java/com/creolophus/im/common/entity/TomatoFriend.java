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
* @date 2020-09-28
*/

public class TomatoFriend extends AbstractEntity  {

	@AutoID
	private Long friendId ;
	private Long leftId ;
	private Long rightId ;
	private Date createTime ;

	public Long getFriendId(){
	    return  friendId;
	}
	public void setFriendId(Long friendId ){
	    this.friendId = friendId;
	}

	public Long getLeftId(){
	    return  leftId;
	}
	public void setLeftId(Long leftId ){
	    this.leftId = leftId;
	}

	public Long getRightId(){
	    return  rightId;
	}
	public void setRightId(Long rightId ){
	    this.rightId = rightId;
	}

	public Date getCreateTime(){
	    return  createTime;
	}
	public void setCreateTime(Date createTime ){
	    this.createTime = createTime;
	}

}
