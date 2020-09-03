package com.creolophus.im.protocol.domain;

/**
 * @author magicnana
 * @date 2019/9/12 下午1:50
 */
public class Command<T> {

    private String token;
    private Header header;
    private T body;
    private Auth auth;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static Command newAck(String sequence, int commandType, Error error) {
        Header header = Header.newAck(sequence, commandType, error);
        Command command = new Command();
        command.setHeader(header);
        return command;

    }

    public static Command newAck(String sequence, int commandType, Object body) {
        Header header = Header.newAck(sequence, commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    public static Command newMsg(int commandType, Object body) {
        Header header = Header.newMsg(commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"token\":\"").append(token).append('\"');
        sb.append(",\"header\":").append(header);
        sb.append(",\"body\":").append(body);
        sb.append(",\"auth\":").append(auth);
        sb.append('}');
        return sb.toString();
    }

    public Command withToken(String token) {
        this.setToken(token);
        return this;
    }
}
