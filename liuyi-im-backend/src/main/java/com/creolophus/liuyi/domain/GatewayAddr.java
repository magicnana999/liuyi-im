package com.creolophus.liuyi.domain;

import com.creolophus.liuyi.common.base.AbstractEntity;
import com.creolophus.liuyi.common.exception.ApiException;

/**
 * @author magicnana
 * @date 2020/2/3 下午3:44
 */
public class GatewayAddr extends AbstractEntity {

    private String ip;
    private int port;

    public static final String SE = ":";

    public GatewayAddr(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public GatewayAddr(String ipseport) {
        String[] array = ipseport.split(SE);
        if(array!=null && array.length==2){
            this.setIp(array[0]);
            this.setPort(Integer.valueOf(array[1]));
        }else{
            throw new ApiException("不正确的地址格式");
        }
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return ip+SE+port;
    }

}
