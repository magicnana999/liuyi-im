package com.creolophus.im.coder;

import com.creolophus.im.protobuf.ProtoCommand;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.Header;
import com.creolophus.liuyi.common.json.JacksonUtil;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/18 6:06 PM
 */
public class ProtoCoder implements MessageCoder {
    @Override
    public <T> T decode(Object body, Class<T> clazz) {
        try {
            return ProtoUtil.decode((Any)body,clazz);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
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
            e.printStackTrace();
        }
        return JacksonUtil.toJava(data, Command.class);
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

    public Command toCommand(ProtoCommand.Command cp) {

        Header header = new Header();
        header.setError(cp.getHeader().getError());
        header.setType(cp.getHeader().getType());
        header.setSeq(cp.getHeader().getSeq());
        header.setCode(cp.getHeader().getCode());

        Command cmd = new Command();
        cmd.setToken(cp.getToken());
        cmd.setHeader(header);
        cmd.setBody(cp.getBody());
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
            builder.setBody(Any.pack((Message)command.getBody()));
        }

        return builder.build();
    }

}
