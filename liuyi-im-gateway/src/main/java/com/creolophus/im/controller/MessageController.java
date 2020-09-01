package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.service.UserClientService;
import com.creolophus.im.type.PushMessageMsg;
import com.creolophus.liuyi.common.api.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2018/12/27 上午11:08
 */

@Validated
@RestController
@RequestMapping(value = "/liuyi/gateway/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private UserClientService userClientService;


    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public ApiResult pushMessage(
            @RequestParam("messageId") Long messageId,
            @RequestParam("messageType") Integer messageType,
            @RequestParam("messageBody") String messageBody,
            @RequestParam("receiverId") Long receiverId,
            @RequestParam("senderId") Long senderId,
            @RequestParam(value = "groupId", required = false) Long groupId) {
        PushMessageMsg down = new PushMessageMsg(messageId, messageType, groupId, messageBody, receiverId, senderId);
        PushMessageMsg ret = userClientService.pushMessage(down);
        return new ApiResult(ret);
    }


}
