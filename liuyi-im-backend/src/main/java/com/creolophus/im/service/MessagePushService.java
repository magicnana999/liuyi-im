package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.Message;
import com.creolophus.liuyi.common.api.ApiError;
import com.creolophus.liuyi.common.api.ApiResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/2/20 上午10:47
 */
@Service
public class MessagePushService extends BaseService {


    @Resource
    private RestTemplate restTemplate;


    public <T> T post(String uri, MultiValueMap<String, Object> parameters, Class<T> responseType) {
        return post(uri, parameters, null, responseType);
    }

    public <T> T post(String uri, MultiValueMap<String, Object> parameters, MultiValueMap<String, String> header, Class<T> responseType) {
        try {
            HttpEntity httpEntity = new HttpEntity(parameters, header);
            ResponseEntity<T> response = restTemplate.postForEntity(uri, httpEntity, responseType);
            return response.getBody();
        } catch (Throwable e) {
            throw new RuntimeException("Gateway不可用", e);
        }
    }

    public <T> T push(String gatewayIp, int gatewayPort, Message message) {
        String url = "http://" + gatewayIp + ":" + gatewayPort + "/liuyi/gateway/message/push";
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap();
        parameters.add("messageId", message.getMessageId());
        parameters.add("messageType", message.getMessageType());
        parameters.add("messageBody", message.getMessageBody());
        parameters.add("receiverId", message.getReceiverId());
        parameters.add("senderId", message.getSenderId());
        parameters.add("groupId", message.getGroupId());
        ApiResult apiResult = post(url, parameters, ApiResult.class);
        if(apiResult == null) {
            throw new RuntimeException("Gateway未返回ApiResult");
        }

        if(apiResult.getCode() != ApiError.S_OK.getCode()) {
            throw new RuntimeException(apiResult.getMessage());
        }

        return (T) apiResult.getData();
    }
}
