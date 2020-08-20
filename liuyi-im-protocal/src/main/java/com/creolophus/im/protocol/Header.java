package com.creolophus.im.protocol;

/**
 * @author magicnana
 * @date 2019/9/12 下午1:50
 */
public class Header {


    private static final int MSG = 0;
    private static final int ACK_OK = 200;
    private String seq;
    private int type;
    private int code;
    /**
     * Error Message if not blank
     */
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 服务端收到客户端的请求后,处理失败,回复的.
     */
    public static Header newAck(String sequence, int commandType, Error error) {
        Header header = new Header();
        header.setSeq(sequence);
        header.setType(commandType);
        header.setCode(error.getCode());
        header.setError(error.getMessage());
        return header;
    }

    /**
     * 服务端收到客户端的请求后,处理成功,回复的.
     */
    public static Header newAck(String sequence, int commandType) {
        Header header = new Header();
        header.setSeq(sequence);
        header.setType(commandType);
        header.setCode(ACK_OK);
        return header;
    }

    private static Header newHeader(int commandType, int code) {
        Header header = new Header();
        header.setSeq(Seq.nextSequence());
        header.setType(commandType);
        header.setCode(code);
        return header;
    }

    /**
     * 客户端发送的
     */
    public static Header newMsg(int commandType) {
        return newHeader(commandType, MSG);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"seq\":\"").append(seq).append('\"');
        sb.append(",\"type\":").append(type);
        sb.append(",\"code\":").append(code);
        sb.append(",\"error\":\"").append(error).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
