package com.creolophus.tomato.controller;

import com.creolophus.im.common.entity.GroupDefine;
import com.creolophus.im.common.entity.GroupMember;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.tomato.base.BaseController;
import com.creolophus.tomato.service.GroupService;
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
@RequestMapping(value = "/liuyiim/tomato/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController extends BaseController {

    @Resource
    private GroupService groupService;

    @RequestMapping(value = "/append", method = RequestMethod.GET)
    public ApiResult appendGroupMember(
            @RequestParam("groupId") Long groupId,
            @RequestParam("memberId") Long memberImId){
            groupService.appendMember(groupId, memberImId);
        return new ApiResult();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult createGroup(
            @RequestParam("targetIds") String targetIds) {
        GroupDefine group = groupService.createGroup(getImId(), targetIds);
        return new ApiResult(group);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult queryGroupList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        List<GroupDefine> groupDefines = groupService.queryGroupList(getImId(), pageNo, pageSize);
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
