package com.creolophus.tomato.controller;

import com.creolophus.im.common.entity.TomatoFriend;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.tomato.base.BaseController;
import com.creolophus.tomato.service.FriendService;
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
@RequestMapping(value = "/liuyiim/tomato/friend", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FriendController extends BaseController {

    @Resource
    private FriendService friendService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult createGroup(
            @RequestParam("targetId") Long targetId) {
        friendService.createFriend(getUserId(),targetId);
        return new ApiResult();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult queryFriendList(
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        List<TomatoFriend> groupDefines = friendService.queryFriendList(getUserId(), pageNo, pageSize);
        return new ApiResult(groupDefines);
    }
}
