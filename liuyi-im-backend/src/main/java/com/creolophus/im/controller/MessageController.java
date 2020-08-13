package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.common.config.LiuyiSetting;
import com.creolophus.im.service.MessageService;
import com.creolophus.im.vo.SimpleMessageVo;
import com.creolophus.liuyi.common.api.ApiResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/liuyi/backend/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageController extends BaseController {

    @Resource
    private MessageService messageService;

    /**
     * 取得单个会话的消息列表
     * Application 调用
     * @param receiverId  消息接收人 ID
     * @param messageId   当前会话的最新 messageId
     * @param messageType [1:单聊,2:群聊]
     * @param sourceId    来源 ID [单聊:senderId,群聊:groupId]
     * @param pageNo      当前页
     * @param pageSize    每页数量
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult findMessageList(
            @RequestParam("receiverId") Long receiverId,
            @RequestParam("messageId") Long messageId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam("sourceId") Long sourceId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<SimpleMessageVo> list = messageService.findMessageList(receiverId, messageId, messageType, sourceId, pageNo, pageSize);
        return new ApiResult(list);
    }


    /**
     * Gateway 调用,发送和同步消息
     * @param appKey      AppKey
     * @param senderId    发送人 ID
     * @param messageType [1:单聊,2:群聊]
     * @param targetId    [单聊:receiverId,群聊:groupId]
     * @param sendTime    发送时间
     * @param messageBody 消息体
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ApiResult sendMessage(
            @RequestParam("appKey") String appKey,
            @RequestParam("senderId") Long senderId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam("targetId") Long targetId,
            @DateTimeFormat(pattern = LiuyiSetting.MESSAGE_SEND_TIME_PATTERN) @RequestParam("sendTime") Date sendTime,
            @RequestParam("messageBody") String messageBody) {
        Long messageId = messageService.sendMessage(senderId, targetId, messageType, messageBody, sendTime, appKey);
        return new ApiResult(messageId);
    }
}
