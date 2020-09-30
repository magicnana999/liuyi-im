package com.creolophus.tomato.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.tomato.base.BaseController;
import com.creolophus.tomato.entity.TomatoUser;
import com.creolophus.tomato.service.UserService;
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
@RequestMapping(value = "/liuyiim/tomato/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 如果显示的指定 userId,那么可以查任何人的 TomatoUser 信息,否则从 token 中取得自己的.
     * 但是这有个权限的问题.解决方式就是隐私信息不要放到 tomatoUser 中,可以另外建立 tomatoUserInfo之类的.
     * 用于显示任何人的头像,ID,名字,手机号码等信息.
     * @param userId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult getUserInfo(@RequestParam(value = "userId",required = false) Long userId) {
        TomatoUser tomatoUser = userService.findUserByUserId(userId==null?getUserId():userId);
        return new ApiResult(tomatoUser);
    }
}
