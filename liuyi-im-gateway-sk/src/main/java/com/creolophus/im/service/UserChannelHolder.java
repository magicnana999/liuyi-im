package com.creolophus.im.service;

import com.creolophus.im.domain.UserChannel;
import com.creolophus.im.domain.UserClient;
import com.creolophus.im.io.LoginInput;
import com.creolophus.im.io.LoginOutput;
import com.creolophus.im.io.PushMessageOutput;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.processor.UserClientProcessor;
import com.creolophus.im.feign.BackendFeign;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

//import com.creolophus.liuyi.netty.context.RemoteContextValidator;

/**
 * @author magicnana
 * @date 2019/1/23 下午5:38
 */
@Component
public class UserChannelHolder extends NettyBaseService implements UserClientProcessor {

    private static final Logger logger = LoggerFactory.getLogger(UserChannelHolder.class);


    private static final ConcurrentHashMap<Long/*userId*/, UserChannel> userTable = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String/*channelId*/, UserChannel> channelTable = new ConcurrentHashMap();

    @Resource
    private BackendFeign backendFeign;

    public UserChannel getUserClient(Long userId) {
        return userTable.get(userId);
    }

    public UserChannel getUserClient(String channelId){
        return channelTable.get(channelId);
    }

    private void insertChannelTable(UserChannel client) {
        channelTable.put(client.getChannelId(), client);
    }

    private void insertUserTable(UserChannel client) {
        UserChannel origin = userTable.putIfAbsent(client.getUserId(), client);
        if(origin != null) {
            origin.getChannel().close().addListener((ChannelFutureListener) future -> logger.debug("channel has been closed {}", origin.getChannel()));
        }
    }

    @Override
    public void registerUserClient(UserClient userClient) {
        UserChannel client = (UserChannel)userClient;
        insertUserTable(client);
        insertChannelTable(client);
    }

    private void removeChannelTable(UserChannel client) {
        channelTable.remove(client.getChannelId());
    }

    private void removeUserTable(UserChannel client) {
        userTable.remove(client.getUserId());
    }

    public void unregisterUserClient(UserChannel client) {
        backendFeign.unregisterUserClient("127.0.0.1", 33010, client.getUserId());
        removeUserTable(client);
        removeChannelTable(client);
    }

    @Override
    public Long pushMessage(Long messageId, Integer messageType, String messageBody, Long receiverId, Long groupId, Long senderId){
        Command response = Command.newResponse(CommandType.PUSH_MESSAGE.getValue(), new PushMessageOutput(messageId, messageType, groupId, messageBody, receiverId, senderId));

        pushMessage(receiverId, response);
        return messageId;
    }


    public void pushMessage(Long receiverId, Command response){
        if(response != null) {
//            remoteContextValidator.setResponse(response);
            try {
                UserChannel uc = getUserClient(receiverId);
                if(uc!=null && uc.getChannel()!=null){
                    uc.getChannel().writeAndFlush(response);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Nothing to response");
        }
    }

    @Override
    public LoginOutput connect(LoginInput input) {
        UserChannel client = new UserChannel();
        client.setChannel(getChannel());
        client.setUserId(getUserId());
        client.setAppKey(getAppKey());
        client.setSocketType(UserChannel.SocketType.SOCKET.getValue());
        registerUserClient(client);
        backendFeign.registerUserClient("127.0.0.1",33010,client.getUserId());

        LoginOutput ret = new LoginOutput();
        ret.setAppKey(getAppKey());
        ret.setToken(getToken());
        ret.setUserId(getUserId());
        return ret;
    }

}
