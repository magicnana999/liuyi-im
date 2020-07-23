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

public class MessageIdSectionUser extends AbstractEntity  {

	@AssignID
	private Long userId ;
	private Long sectionId ;

	public Long getUserId(){
	    return  userId;
	}
	public void setUserId(Long userId ){
	    this.userId = userId;
	}

	public Long getSectionId(){
	    return  sectionId;
	}
	public void setSectionId(Long sectionId ){
	    this.sectionId = sectionId;
	}

}
