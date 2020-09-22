package com.creolophus.tomato.feign;

import com.creolophus.im.common.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author magicnana
 * @date 2018/12/20 下午4:00
 */
@FeignClient(value = "liuyi-im-backend", url = "${k8s.liuyi-im-backend}", fallback = Void.class)
public interface BackendFeign {

    @RequestMapping(value = "/liuyiim/backend/message/list", method = RequestMethod.GET)
    List<Message> getMessageList(
            @RequestParam("receiverId") Long receiverId,
            @RequestParam(value = "messageId", required = false, defaultValue = "0") Long messageId,
            @RequestParam(value = "groupId", required = false) Long groupId,
            @RequestParam(value = "senderId", required = false) Long senderId,
            @RequestParam(value = "messageType", required = false) Integer messageType,
            @RequestParam(value = "messageKind", required = false) Integer messageKind,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize);

    @RequestMapping(value = "/liuyiim/backend/auth/token", method = RequestMethod.GET)
    String login(@RequestParam(value = "userId") Long userId);

    @RequestMapping(value = "/liuyiim/backend/user/new", method = RequestMethod.GET)
    Long syncUser(
            @RequestParam(value = "name") String name, @RequestParam(value = "portrait") String portrait, @RequestParam(value = "outerId") Long outerId);
}
