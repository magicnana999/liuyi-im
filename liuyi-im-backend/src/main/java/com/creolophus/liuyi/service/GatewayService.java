package com.creolophus.liuyi.service;

import com.creolophus.liuyi.common.base.BaseService;
import com.creolophus.liuyi.domain.GatewayAddr;
import com.creolophus.liuyi.storage.GatewayStorage;
import com.creolophus.liuyi.vo.ValidateGatewayVo;
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

    public void registerGateway(String ip, Integer socketPort) {
        gatewayStorage.registerGateway(ip,socketPort);
    }

    public void unregisterGateway(String ip, Integer port) {
        gatewayStorage.unregisterGateway(ip,port);
    }

    public void findGetewayListAndCheck(){
        Set<String> set = gatewayStorage.getGatewayList();
        for(String ipAndPort : set){
            GatewayAddr addr = new GatewayAddr(ipAndPort);
            if(!validateGateway(addr)){
                unregisterGateway(addr.getIp(),addr.getPort());
                logger.info("Gateway 下线 {}", ipAndPort);
            }
        }
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