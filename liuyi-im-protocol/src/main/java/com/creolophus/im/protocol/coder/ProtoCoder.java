package com.creolophus.im.protocol.coder;

import com.creolophus.im.protocol.domain.Command;
import com.creolophus.im.protocol.domain.Header;
import com.creolophus.im.protocol.protobuf.ProtoCommand;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/18 6:06 PM
 */
public class ProtoCoder implements MessageCoder {
    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        return (T) body;
    }

    @Override
    public Command decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();
        byte[] data = new byte[length];
        byteBuffer.get(data);
        try {
            ProtoCommand.Command cp = ProtoCommand.Command.parseFrom(data);
            return toCommand(cp);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ByteBuffer encode(Command command) {
        // 1> header length size
        int length = 4;

        // 2> header data length
        ProtoCommand.Command cmd = toCommand(command);
        byte[] bytes = cmd.toByteArray();
        length += bytes.length;

        ByteBuffer result = ByteBuffer.allocate(length);

        // length
        result.putInt(bytes.length);

        // header data
        result.put(bytes);
        result.flip();

        return result;
    }

    @Override
    public String toString(Object command) {
        ProtoCommand.Command cmd = (ProtoCommand.Command) command;
        JsonFormat jsonFormat = new JsonFormat();
        return jsonFormat.printToString(cmd);
    }

    public <T> T decode(Object obj) throws InvalidProtocolBufferException {
        return ProtoUtil.decode((Any) obj);
    }

    public <T> T encode(Object obj) {
        return ProtoUtil.encode(obj);
    }

    public Command toCommand(ProtoCommand.Command cp) throws InvalidProtocolBufferException {

        Header header = new Header();
        header.setError(cp.getHeader().getError());
        header.setType(cp.getHeader().getType());
        header.setSeq(cp.getHeader().getSeq());
        header.setCode(cp.getHeader().getCode());

        Command cmd = new Command();
        cmd.setToken(cp.getToken());
        cmd.setHeader(header);
        cmd.setBody(this.decode(cp.getBody()));
        return cmd;
    }

    public ProtoCommand.Command toCommand(Command command) {
        if(command == null){
            throw new NullPointerException();
        }

        ProtoCommand.Command.Builder builder = ProtoCommand.Command.newBuilder();
        if(command.getHeader()!=null){
            Header header = command.getHeader();
            ProtoCommand.Command.Header.Builder headerBuilder = ProtoCommand.Command.Header.newBuilder();
            headerBuilder.setCode(header.getCode()).
                    setType(header.getType())
                    .setSeq(header.getSeq());
            if(StringUtils.isNotBlank(header.getError())){
                headerBuilder.setError(header.getError());
            }
            builder.setHeader(headerBuilder);
        }

        if(StringUtils.isNotBlank(command.getToken())){
            builder.setToken(command.getToken());
        }

        if(null != command.getBody()){
            builder.setBody(Any.pack(this.encode(command.getBody())));
        }

        return builder.build();
    }

}
