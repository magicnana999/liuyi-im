package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.common.entity.User;
import com.creolophus.im.service.UserService;
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
@RequestMapping(value = "/liuyi/backend/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ApiResult createUser(
            @RequestParam("name") String name,
            @RequestParam(value = "portrait", required = false) String portrait,
            @RequestParam("outerId") Long outerId) {
        Long userId = userService.createUser(name, portrait, outerId, currentAppKey());
        return new ApiResult(userId);
    }

    @RequestMapping(value = "/get_by_id", method = RequestMethod.GET)
    public ApiResult getUser(@RequestParam("userId") Long userId) {
        User user = userService.findUserByUserId(userId);
        return new ApiResult(user);
    }

}
