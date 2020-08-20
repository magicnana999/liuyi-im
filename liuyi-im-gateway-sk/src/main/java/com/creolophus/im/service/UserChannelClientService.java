package com.creolophus.im.service;

import com.creolophus.im.domain.UserChannel;
import com.creolophus.im.domain.UserClient;
import com.creolophus.im.feign.BackendFeign;
import com.creolophus.im.netty.core.ContextProcessor;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.type.LoginAck;
import com.creolophus.im.type.LoginMsg;
import io.netty.channel.Channel;
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
public class UserChannelClientService extends UserClientService {

    private static final Logger logger = LoggerFactory.getLogger(UserChannelClientService.class);


    private static final ConcurrentHashMap<Long/*userId*/, UserChannel> userTable = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String/*channelId*/, UserChannel> channelTable = new ConcurrentHashMap();

    @Resource
    private BackendFeign backendFeign;

    @Resource
    private ContextProcessor contextProcessor;

    private String getAppKey() {
        return contextProcessor.getAppKey();
    }

    private Channel getChannel() {
        return contextProcessor.getChannel();
    }

    private String getToken() {
        return contextProcessor.getToken();
    }

    public UserChannel getUserClient(Long userId) {
        return userTable.get(userId);
    }

    public UserChannel getUserClient(String channelId) {
        return channelTable.get(channelId);
    }

    private Long getUserId() {
        return contextProcessor.getUserId();
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
    public LoginAck login(LoginMsg input) {
        UserChannel client = new UserChannel();
        client.setChannel(getChannel());
        client.setUserId(getUserId());
        client.setAppKey(getAppKey());
        client.setSocketType(UserChannel.SocketType.SOCKET.getValue());
        registerUserClient(client);
        backendFeign.registerUserClient("127.0.0.1", 33010, client.getUserId());

        LoginAck ret = new LoginAck();
        ret.setAppKey(getAppKey());
        ret.setToken(getToken());
        ret.setUserId(getUserId());

        logger.info("用户登录 成功 {}.{}", ret.getAppKey(), ret.getUserId());

        return ret;
    }

    @Override
    public void pushMessage(Long receiverId, Command response) {
        if(response != null) {
            try {
                UserChannel uc = getUserClient(receiverId);
                if(uc != null && uc.getChannel() != null) {
                    uc.getChannel().writeAndFlush(response);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Nothing to response");
        }
    }


    public void registerUserClient(UserClient userClient) {
        UserChannel client = (UserChannel) userClient;
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
        logger.info("用户断开 完成 {}.{}", client.getAppKey(), client.getUserId());
    }

}
