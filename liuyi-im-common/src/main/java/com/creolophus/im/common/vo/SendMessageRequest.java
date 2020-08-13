package com.creolophus.im.common.vo;

import com.creolophus.liuyi.common.base.AbstractEntity;

/**
 * @author magicnana
 * @date 2019/10/31 下午2:44
 */
public class SendMessageRequest extends AbstractEntity {

    private Integer messageType;
    private Long senderId;
    private String conversationId;
    private String messageBody;


}



