package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.common.entity.Group;
import com.creolophus.im.service.GroupService;
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
@RequestMapping(value = "/liuyi/backend/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController extends BaseController {

    @Resource
    private GroupService groupService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ApiResult createGroup(
            @RequestParam("userId") Long userId, @RequestParam("targetIds") String targetIds) {
        Group group = groupService.createGroup(userId, targetIds);
        return new ApiResult(group);
    }

}
