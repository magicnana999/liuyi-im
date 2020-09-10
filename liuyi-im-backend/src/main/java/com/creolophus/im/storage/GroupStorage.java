package com.creolophus.im.storage;

import com.creolophus.im.common.base.BaseStorage;
import com.creolophus.im.common.entity.GroupMember;
import com.creolophus.liuyi.common.redis.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author magicnana
 * @date 2019/10/21 上午11:23
 */
@Service
public class GroupStorage extends BaseStorage {

    private static final String GROUP_MEMBERS = PREFIX + "group_members" + SE;
    private static final String GROUP_MEMBERS_LOCK = GROUP_MEMBERS + "lock" + SE;
    private static final int GROUP_MEMBERS_EXPIRE = DAY * 15;
    private static final int GROUP_MEMBERS_LOCK_EXPIRE = SECOND * 3;


    @Resource
    private RedisClient redisClient;

    public List<Long> getGroupMembers(Long groupId) {
        List<String> list = redisClient.lrange(getGroupMembersKey(groupId), 0, Integer.MAX_VALUE);
        if(list == null || list.size() == 0) {
            return Collections.emptyList();
        } else {
            return list.stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
        }
    }

    private String getGroupMembersKey(Long groupId) {
        Objects.requireNonNull(groupId, "groupId 为空,无法生成 Group Key");
        return GROUP_MEMBERS + groupId;
    }

    private String getGroupMembersLockKey(Long groupId) {
        Objects.requireNonNull(groupId, "groupId 为空,无法生成 GroupMembersLock Key");
        return GROUP_MEMBERS_LOCK + groupId;
    }

    public boolean lockGroupMembers(Long groupId) {
        String ret = redisClient.lock(getGroupMembersLockKey(groupId), GROUP_MEMBERS_LOCK_EXPIRE);
        return StringUtils.isNotBlank(ret);
    }

    public Long setGroupMembersWithGM(Long groupId, List<GroupMember> list) {
        if(list == null || list.size() == 0) return 0L;
        String key = getGroupMembersKey(groupId);
        long affected = 0;
        for (GroupMember gm : list) {
            affected += redisClient.rpush(key, String.valueOf(gm.getUserId()));
        }
        redisClient.expire(key, GROUP_MEMBERS_EXPIRE);
        return affected;
    }

    public Long setGroupMembersWithGM(Long groupId, GroupMember list) {
        if(list == null) return 0L;
        String key = getGroupMembersKey(groupId);
        long affected = redisClient.rpush(key, String.valueOf(list.getUserId()));
        redisClient.expire(key, GROUP_MEMBERS_EXPIRE);
        return affected;
    }

    public Long setGroupMembersWithID(Long groupId, Long userId) {
        if(userId == null) return 0L;
        String key = getGroupMembersKey(groupId);
        long size = redisClient.rpush(key, String.valueOf(userId));
        redisClient.expire(key, GROUP_MEMBERS_EXPIRE);
        return size;
    }

    public Long setGroupMembersWithID(Long groupId, List<Long> list) {
        if(list == null || list.size() == 0) return 0L;
        String key = getGroupMembersKey(groupId);

        long affected = 0;
        for (Long gm : list) {
            affected += redisClient.rpush(key, String.valueOf(gm));
        }
        redisClient.expire(key, GROUP_MEMBERS_EXPIRE);
        return affected;
    }

    public boolean unlockGroupMember(Long groupId) {
        return redisClient.unlock(getGroupMembersLockKey(groupId), null);
    }

}
