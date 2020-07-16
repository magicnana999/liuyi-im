package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.BaseController;
import com.creolophus.liuyi.common.entity.Group;
import com.creolophus.liuyi.common.entity.User;
import com.creolophus.liuyi.service.GroupService;
import com.creolophus.liuyi.service.UserService;
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
            @RequestParam(value = "portrait",required = false) String portrait,
            @RequestParam("outerId") Long outerId
            ) {
        Long userId = userService.createUser(name,portrait,outerId);
        return new ApiResult(userId);
    }
}
