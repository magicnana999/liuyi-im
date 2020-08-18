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

//    public static Command decode(final byte[] array) {
//        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
//        return decode(byteBuffer);
//    }
//
//    public static Command decode(final ByteBuffer byteBuffer) {
//        int length = byteBuffer.getInt();
//
//        byte[] data = new byte[length];
//        byteBuffer.get(data);
//
//        Command cmd = JSON.parseObject(data, Command.class);
//        return cmd;
//    }
//
//    public ByteBuffer encode() {
//        // 1> header length size
//        int length = 4;
//
//        // 2> header data length
//        byte[] bytes = JSON.toJSONBytes(this);
//        length += bytes.length;
//
//        ByteBuffer result = ByteBuffer.allocate(length);
//
//        // length
//        result.putInt(bytes.length);
//
//        // header data
//        result.put(bytes);
//        result.flip();
//
//        return result;
//    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static Command newRequest(int commandType, Object body) {
        Header header = Header.newRequest(commandType);
        Command command = new Command();
        command.setHeader(header);
        command.setBody(body);
        return command;
    }

    public static Command newResponse(String sequence, int commandType, Error error) {
        Header header = Header.newResponse(sequence, commandType, error);
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
