package com.creolophus.liuyi.netty.protocol;

import com.creolophus.liuyi.common.id.ObjectID;
import com.creolophus.liuyi.netty.exception.NettyError;

/**
 * @author magicnana
 * @date 2019/9/12 下午1:50
 */
public class Header {

    private static final ObjectID ID_GENERATOR = new ObjectID();

    public static final int SUCCESS=NettyError.S_OK.getCode();


    /**
     * RequestID
     */
    private String opaque;

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

    public String getOpaque() {
        return opaque;
    }
    public void setOpaque(String opaque) {
        this.opaque = opaque;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public static Header newHeader(int commandType) {
        Header header = new Header();
        header.setOpaque(ID_GENERATOR.nextId());
        header.setType(commandType);
        header.setCode(SUCCESS);
        return header;
    }

    public static Header newHeader(String opaque, int commandType, NettyError apiErrorCode) {
        Header header = new Header();
        header.setOpaque(opaque);
        header.setType(commandType);
        header.setCode(apiErrorCode.getCode());
        header.setError(apiErrorCode.getMessage());
        return header;
    }

    public static Header newHeader(String opaque, int commandType) {
        Header header = new Header();
        header.setOpaque(opaque);
        header.setType(commandType);
        header.setCode(SUCCESS);
        return header;
    }

}
