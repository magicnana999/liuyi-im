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

    public IPAndPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getID() {
        return ip + SEP + port;
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

    @Override
    public String toString() {
        return getID();
    }


}
