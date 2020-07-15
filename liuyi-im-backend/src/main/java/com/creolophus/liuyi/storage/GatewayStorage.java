package com.creolophus.liuyi.storage;

import com.creolophus.liuyi.common.base.BaseStorage;
import com.creolophus.liuyi.common.redis.RedisClient;
import com.creolophus.liuyi.common.util.Strings;
import com.creolophus.liuyi.domain.GatewayAddr;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Set;

/**
 * @author magicnana
 * @date 2019/5/21 上午10:17
 */
@Service
public class GatewayStorage extends BaseStorage {

    private static final String GATEWAY_SET = PREFIX + "gateway_set";
    private static final String GATEWAY_CLIENT = PREFIX + "gateway_clients" + SE;

    @Resource
    private RedisClient redisClient;

    public GatewayAddr getUserClient(Long userId) {
        Set<String> line = getGatewayList();
        for(String ipAndPort : line){
            if(getUserClientExist(ipAndPort,userId)){
                return new GatewayAddr(ipAndPort);
            }
        }
        return null;
    }

    public boolean getUserClientExist(String ipAndPort,Long userId){
        return redisClient.sismember(getUserClientKey(ipAndPort),String.valueOf(userId));
    }

    private String getGatewayKey() {
        return GATEWAY_SET;
    }

    public Set<String> getGatewayList() {
        return redisClient.smembers(getGatewayKey());
    }

    private String getUserClientKey(String gatewayIp,Integer gatewayPort) {
        Strings.requireNonBlank(gatewayIp, "gatewayIp 为空,无法生成 UserClientKey");
        Objects.requireNonNull(gatewayPort, "gatewayPort 为空,无法生成 UserClientKey");
        return GATEWAY_CLIENT + ipWithPort(gatewayIp, gatewayPort);
    }

    private String getUserClientKey(String ipAndPort){
        Strings.requireNonBlank(ipAndPort, "ipAndPort 为空,无法生成 UserClientKey");
        return GATEWAY_CLIENT + ipAndPort;
    }

    private String ipWithPort(String ip, Integer port) {
        return new GatewayAddr(ip, port).getAddress();
    }

    public Long registerGateway(String ip, Integer socketPort) {
        return redisClient.sadd(getGatewayKey(), ipWithPort(ip, socketPort));
    }

    /**
     * @param gatewayIp
     * @param gatewayPort
     * @param userId
     * @return affected records
     */
    public Long registerUserClient(String gatewayIp,Integer gatewayPort,Long userId) {
        if(userId==null) return 0L;
        return redisClient.sadd(getUserClientKey(gatewayIp,gatewayPort),String.valueOf(userId));
    }

    public Long unregisterGateway(String ip, Integer socketPort) {
        return redisClient.srem(getGatewayKey(), ipWithPort(ip, socketPort));
    }

    public Long unregisterUserClient(String gatewayIp,Integer gatewayPort,Long userId) {
        if(userId==null) return 0L;
        return redisClient.srem(getUserClientKey(gatewayIp,gatewayPort),String.valueOf(userId));
    }
}