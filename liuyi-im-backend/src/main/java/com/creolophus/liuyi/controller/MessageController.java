package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.BaseController;
import com.creolophus.liuyi.common.config.LiuyiSetting;
import com.creolophus.liuyi.common.entity.Message;
import com.creolophus.liuyi.service.MessageService;
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

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    public ApiResult getUnread(
            @RequestParam("senderId") Long senderId,
            @RequestParam("targetId") Long targetId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam(value = "messageId", required = false, defaultValue = "0") Long messageId) {
        List<Message> list = messageService.findUnreadMessageList(senderId, targetId, messageType, messageId);
        return new ApiResult(list);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ApiResult sendMessage(
            @RequestParam("senderId") Long senderId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam("targetId") Long targetId,
            @DateTimeFormat(pattern = LiuyiSetting.MESSAGE_SEND_TIME_PATTERN) @RequestParam("sendTime") Date sendTime,
            @RequestParam("messageBody") String messageBody) {
        Long messageId = messageService.sendMessage(senderId, targetId, messageType, messageBody, sendTime);
        return new ApiResult(messageId);
    }
}
