package com.creolophus.im.sdk;

/**
 * @author magicnana
 * @date 2020/7/30 上午11:45
 */
public class Configration {

    private boolean isDev = true;
    private String ip = "127.0.0.1";
    private int port = 33009;

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

    public boolean isDev() {
        return isDev;
    }

    public void setDev(boolean dev) {
        isDev = dev;
    }
}
