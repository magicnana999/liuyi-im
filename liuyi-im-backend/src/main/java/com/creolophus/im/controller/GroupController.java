package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.common.entity.GroupDefine;
import com.creolophus.im.common.entity.GroupMember;
import com.creolophus.im.service.GroupService;
import com.creolophus.liuyi.common.api.ApiResult;
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
@RequestMapping(value = "/liuyiim/backend/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController extends BaseController {

    @Resource
    private GroupService groupService;

    @RequestMapping(value = "/append", method = RequestMethod.GET)
    public ApiResult appendGroupMemberList(
            @RequestParam("groupId") Long groupId, @RequestParam("targetId") Long targetId) {
        groupService.appendGroupMember(groupId, targetId);
        return new ApiResult();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ApiResult createGroup(
            @RequestParam("userId") Long userId, @RequestParam("targetIds") String targetIds) {
        GroupDefine groupDefine = groupService.createGroup(userId, targetIds);
        return new ApiResult(groupDefine);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult queryGroupList(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        List<GroupDefine> groupDefines = groupService.queryGroupList(userId, pageNo, pageSize);
        return new ApiResult(groupDefines);
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public ApiResult queryGroupMemberList(
            @RequestParam("groupId") Long groupId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        List<GroupMember> list = groupService.queryGroupMemberList(groupId, pageNo, pageSize);
        return new ApiResult(list);
    }


}
