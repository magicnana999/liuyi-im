package com.creolophus.im.feign;

import com.creolophus.im.common.security.UserSecurity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author magicnana
 * @date 2018/12/20 下午4:00
 */
@FeignClient(value = "liuyi-im-backend", url = "${k8s.liuyi-im-backend}", fallback = BackendFeignHystrix.class)
public interface BackendFeign {

    @RequestMapping(value = "/liuyiim/backend/gateway/register", method = RequestMethod.POST)
    void registerGateway(
            @RequestParam(value = "ip") String ip, @RequestParam(value = "port") Integer port);

    @RequestMapping(value = "/liuyiim/backend/user_client/register", method = RequestMethod.POST)
    void registerUserClient(
            @RequestParam("gatewayIp") String gatewayIp, @RequestParam("gatewayPort") Integer gatewayPort, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/liuyiim/backend/message/send", method = RequestMethod.POST)
    Long sendMessage(
            @RequestParam("appKey") String appKey,
            @RequestParam("senderId") Long senderId,
            @RequestParam("messageType") Integer messageType, @RequestParam("messageKind") Integer messageKind,
            @RequestParam("targetId") Long targetId,
            @RequestParam("sendTime") String sendTime,
            @RequestParam("messageBody") String messageBod);

    @RequestMapping(value = "/liuyiim/backend/gateway/unregister", method = RequestMethod.POST)
    void unregisterGateway(
            @RequestParam(value = "ip") String ip, @RequestParam(value = "port") Integer port);

    @RequestMapping(value = "/liuyiim/backend/user_client/unregister", method = RequestMethod.POST)
    void unregisterUserClient(
            @RequestParam("gatewayIp") String gatewayIp, @RequestParam("gatewayPort") Integer gatewayPort, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/liuyiim/backend/auth/verify", method = RequestMethod.GET)
    UserSecurity verifyToken(@RequestParam(value = "token") String token);
}
