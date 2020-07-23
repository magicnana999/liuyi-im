package com.creolophus.im.controller;

import com.creolophus.im.service.AuthService;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.common.security.UserSecurity;
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
@RequestMapping(value = "/liuyi/backend/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController extends BaseController {

    @Resource
    private AuthService authService;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ApiResult verifyToken(
            @RequestParam("token") String token) {
        UserSecurity user = authService.verifyToken(token);
        return new ApiResult(user);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ApiResult getToken(
            @RequestParam("userId") Long userId) {
        UserSecurity user = authService.createToken(userId);
        return new ApiResult(user.getToken());
    }

}
