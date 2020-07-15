package com.creolophus.liuyi.domain;

import com.creolophus.liuyi.common.base.AbstractVo;

/**
 * @author magicnana
 * @date 2020/7/14 下午2:55
 */
public class UserClient {
    private String appKey;
    private Long userId;
    private Integer socketType;

    public String getAppKey() {
        return appKey;
    }
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSocketType() {
        return socketType;
    }
    public void setSocketType(Integer socketType) {
        this.socketType = socketType;
    }

    public enum SocketType{
        SOCKET(1),
        WEBSOCKET(2),;

        int value;

        public int getValue() {
            return value;
        }

        SocketType(int value){
            this.value=value;
        }


        public static SocketType valueOf(Integer value){
            if(value ==null || value == 0){
                return null;
            }
            for(SocketType st : SocketType.values()){
                if(st.value == value){
                    return st;
                }
            }
            return null;
        }

    }

}
