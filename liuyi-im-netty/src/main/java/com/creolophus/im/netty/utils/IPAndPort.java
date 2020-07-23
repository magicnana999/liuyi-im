package com.creolophus.im.netty.utils;

/**
 * @author magicnana
 * @date 2019/9/17 下午2:00
 */
public class IPAndPort {

    public static final String SEP = ":";


    private String ip;
    private int port;

    public IPAndPort() {
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public IPAndPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getID() {
        return ip + SEP + port;
    }

    @Override
    public String toString() {
        return getID();
    }


}
