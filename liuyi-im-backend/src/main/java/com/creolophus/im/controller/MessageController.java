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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult findMessageList(
            @RequestParam("receiverId") Long receiverId,
            @RequestParam("messageId") Long messageId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam("senderId") Long senderId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<SimpleMessageVo> list = messageService.findMessageList(receiverId, messageId, messageType, senderId, pageNo, pageSize);
        return new ApiResult(list);
    }


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
