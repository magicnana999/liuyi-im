package com.creolophus.liuyi.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.base.BaseController;
import com.creolophus.liuyi.service.GatewayService;
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
@RequestMapping(value = "/liuyi/backend/gateway", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GatewayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

    @Resource
    private GatewayService gatewayService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult registerGateway(
            @RequestParam("ip") String ip,
            @RequestParam("port") Integer port) {
        gatewayService.registerGateway(ip, port);
        return new ApiResult();
    }

    /**
     * Gateway停止时调用，意外停止有Schedule感知
     * @param ip
     * @param port
     * @return
     */
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public ApiResult unregisterGateway(
            @RequestParam("ip") String ip,
            @RequestParam("port") Integer port) {
        gatewayService.unregisterGateway(ip, port);
        return new ApiResult();
    }

}