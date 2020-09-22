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
@RequestMapping(value = "/tomato/server/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult getUserInfo(@RequestParam(value = "userId",required = false) Long userId) {
        TomatoUser tomatoUser = userService.findUserByUserId(userId==null?getUserId():userId);
        return new ApiResult(tomatoUser);
    }
}
