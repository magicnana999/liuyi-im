package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2019/9/12 下午1:50
 */
public class Command {

    private String token;
    private Header header;
    private Object body;
    private Auth auth;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Header getHeader() {
        return header;
    }
    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }

    public Auth getAuth() {
        return auth;
    }
    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Command withToken(String token){
        this.setToken(token);
        return this;
    }

    public static Command newRequest(int commandType, Object body) {
        Header header = Header.newRequest(commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    public static Command newResponse(String sequence, int commandType, Error error) {
        Header header = Header.newResponse(sequence, commandType,error);
        Command command = new Command();
        command.setHeader(header);
        return command;

    }

    public static Command newResponse(String sequence, int commandType, Object body) {
        Header header = Header.newResponse(sequence, commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    public static Command newResponse(int commandType, Object body) {
        Header header = Header.newResponse(commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

}
