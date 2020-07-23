package com.creolophus.im.netty.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
//import com.creolophus.liuyi.netty.context.RemoteContext;
import com.creolophus.im.netty.exception.NettyCommandException;
import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.netty.protocol.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 下午2:05
 */
public class FastJSONSerializer implements CommandSerializer {

    private static final Logger logger = LoggerFactory.getLogger(FastJSONSerializer.class);

//    @Autowired(required = false)
//    private FastJsonConfig fastJsonConfig;

    @Override
    public <Body> List<Body> bodyFromArray(Object jsonArray, Class<Body> clazz) {
        return ((JSONArray) jsonArray).toJavaList(clazz);
    }

    @Override
    public <Body> Body bodyFromObject(Object jsonObject, Class<Body> clazz) {
        return ((JSONObject) jsonObject).toJavaObject(clazz);
    }

    @Override
    public Command decode(byte[] bytes) {
        if(bytes == null || bytes.length == 0) {
            return null;
        }
        final String json = new String(bytes, Charset.forName("UTF-8")).trim();
//        RemoteContext.getContext().setRequestStr(json);
        try{
            return JSON.parseObject(json, Command.class);
        }catch(Throwable e){
            throw new NettyCommandException(NettyError.E_REQUEST_BODY_VALIDATE_ERROR.format(json));
        }
    }

    @Override
    public byte[] encode(Command t) {
        final String json = toString(t);
        if(json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    @Override
    public String toString(Command command) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Command.class, "header", "body", "token");
        final String json = JSON.toJSONString(command, filter);
        return json;
    }

//    @Override
//    public <T> T body(Object body, Class<T> clazz) {
//        JSONObject jsonObject = (JSONObject) body;
//        return jsonObject.toJavaObject(clazz);
//    }

    @Override
    public String name() {
        return "JSON";
    }
}
