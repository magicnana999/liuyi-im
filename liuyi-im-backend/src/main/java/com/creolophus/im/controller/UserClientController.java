package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.im.service.UserClientService;
import com.creolophus.liuyi.common.api.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author magicnana
 * @date 2018/12/27 上午11:08
 */

@Valid
@RestController
@RequestMapping(value = "/liuyiim/backend/user_client", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserClientController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserClientController.class);

    @Resource
    private UserClientService userClientService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult registerUser(
            @RequestParam("gatewayIp") String gatewayIp, @RequestParam("gatewayPort") Integer gatewayPort, @RequestParam("userId") Long userId) {
        userClientService.registerUserClient(gatewayIp, gatewayPort, userId);
        return new ApiResult();
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public ApiResult unregisterUser(
            @RequestParam("gatewayIp") String gatewayIp, @RequestParam("gatewayPort") Integer gatewayPort, @RequestParam("userId") Long userId) {
        userClientService.unregisterUserClient(gatewayIp, gatewayPort, userId);
        return new ApiResult();
    }
}