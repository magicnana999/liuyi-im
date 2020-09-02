package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.domain.GatewayAddr;
import com.creolophus.im.storage.GatewayStorage;
import com.creolophus.im.vo.ValidateGatewayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author magicnana
 * @date 2019/6/20 下午6:27
 */
@Service
public class GatewayService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Resource
    private GatewayStorage gatewayStorage;

    @Resource
    private RestTemplate restTemplate;

    public void findGetewayListAndCheck(){
        Set<String> set = gatewayStorage.getGatewayList();
        for(String ipAndPort : set){
            GatewayAddr addr = new GatewayAddr(ipAndPort);
            if(!validateGateway(addr)){
                unregisterGateway(addr.getIp(),addr.getPort());
                logger.info("网关下线 {}", ipAndPort);
            }else{
                gatewayStorage.registerGateway(addr.getIp(), addr.getPort());
                logger.info("网关上线 {}", ipAndPort);
            }
        }
    }

    public void registerGateway(String ip, Integer socketPort) {
        gatewayStorage.registerGateway(ip, socketPort);
    }

    public void unregisterGateway(String ip, Integer port) {
        gatewayStorage.unregisterGateway(ip, port);
    }

    private boolean validateGateway(GatewayAddr gw) {
        String url = "http://"+gw.getIp()+":"+gw.getPort()+"/actuator/health";
        try{
            ResponseEntity<ValidateGatewayVo> response = restTemplate.getForEntity(url, ValidateGatewayVo.class);
            if(response!=null && response.getStatusCode().value()== HttpStatus.OK.value()){
                return true;
            }else{
                return false;
            }
        }catch (Throwable e){
            logger.error(e.getMessage(),e);
            return false;
        }
    }
}