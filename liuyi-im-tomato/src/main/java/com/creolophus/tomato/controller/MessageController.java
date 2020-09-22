package com.creolophus.tomato.controller;

import com.creolophus.im.common.entity.Message;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.tomato.base.BaseController;
import com.creolophus.tomato.feign.BackendFeign;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/tomato/server/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageController extends BaseController {

    @Resource
    private BackendFeign backendFeign;

    @RequestMapping(value = "/fresh", method = RequestMethod.GET)
    public ApiResult getUserInfo(
            @RequestParam(value = "messageId",required = false) Long messageId,
            @RequestParam(value = "senderId",required = false) Long senderId,
            @RequestParam(value = "messageType",required = false) Integer messageType,
            @RequestParam(value = "groupId",required = false) Long groupId,
            @RequestParam(value = "messageKind",required = false) Integer messageKind,
            @RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = "20") Integer pageSize
    ) {
        List<Message> list = backendFeign.getMessageList(getUserId(), messageId, groupId, senderId, messageType, messageKind, pageNo, pageSize);
        return new ApiResult(list);
    }
}
