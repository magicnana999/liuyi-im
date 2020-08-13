package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.service.MessageIdService;
import com.creolophus.liuyi.common.api.ApiResult;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/liuyi/id/message_id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageIdController extends BaseController {

    @Resource
    private MessageIdService messageIdService;


    /**
     * 用户注册是调用1次即可
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/new_section", method = RequestMethod.POST)
    public ApiResult insertSection(
            @RequestParam(value = "userId") Long userId) {
        messageIdService.createSectionUserIfNotExist(userId);
        return new ApiResult();
    }

    @RequestMapping(value = "/next_id", method = RequestMethod.POST)
    public ApiResult nextMessageId(
            @RequestParam(value = "userId") Long userId) {
        Long id = messageIdService.nextId(userId);
        return new ApiResult(id);
    }

}
