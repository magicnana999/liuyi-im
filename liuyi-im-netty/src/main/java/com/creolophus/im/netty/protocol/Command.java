package com.creolophus.im.netty.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.creolophus.im.netty.exception.NettyError;
import org.apache.commons.lang3.StringUtils;

/**
 * @author magicnana
 * @date 2019/9/12 下午1:50
 */
public class Command {

    private String token;
    private Header header;
    private Object body;
    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @JSONField(serialize = false)
    public String getOpaque() {
        return this.getHeader().getOpaque();
    }

    @JSONField(serialize = false)
    public int getType(){
        return this.getHeader().getType();
    }

    @JSONField(serialize = false)
    public boolean isEmpty(){
        return this.getHeader()==null ||
                this.getHeader().getType()==0 ||
                StringUtils.isBlank(this.getHeader().getOpaque());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static Command newCommand(int commandType, Object body) {
        Header header = Header.newHeader(commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    public static Command newResponse(String opaque, int commandType, NettyError remoteError) {
        Header header = Header.newHeader(opaque, commandType,remoteError);
        Command command = new Command();
        command.setHeader(header);
        return command;

    }

    public static Command newResponse(String opaque, int commandType, Object body) {
        Header header = Header.newHeader(opaque, commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
