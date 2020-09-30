package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.common.entity.GroupDefine;
import com.creolophus.im.common.entity.GroupMember;
import com.creolophus.im.common.entity.User;
import com.creolophus.im.dao.GroupDao;
import com.creolophus.im.dao.GroupMemberDao;
import com.creolophus.im.storage.GroupStorage;
import com.creolophus.liuyi.common.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class GroupService extends BaseService {

    @Resource
    private IdExampleService idExampleService;

    @Resource
    private GroupDao groupDao;

    @Resource
    private GroupMemberDao groupMemberDao;

    @Resource
    private UserService userService;

    @Resource
    private GroupStorage groupStorage;


    public void appendGroupMember(Long groupId, Long targetId) {
        User user = userService.findUserByUserId(targetId);
        if(user == null) {
            throw new ApiException("无此用户 " + targetId);
        }
        GroupMember temp = new GroupMember();
        temp.setGroupId(groupId);
        temp.setUserId(targetId);
        temp.setMemberName(user.getName());
        temp.setCreateTime(new Date());
        groupMemberDao.insert(temp);
    }

    public GroupDefine createGroup(Long userId, String targetIds) {
        GroupDefine groupDefine = new GroupDefine();
        groupDefine.setCreateTime(new Date());
        groupDefine.setGroupId(idExampleService.nextGroupId(userId));
        groupDefine.setGroupName(getDefaultGroupName(userId, targetIds));

        List<GroupMember> members = new ArrayList<>();


        User owner = userService.findUserByUserId(userId);
        GroupMember creator = new GroupMember();
        creator.setCreateTime(groupDefine.getCreateTime());
        creator.setGroupId(groupDefine.getGroupId());
        creator.setUserId(userId);
        creator.setMemberName(owner == null ? null : owner.getName());
        members.add(creator);


        String[] array = splitTargetIds(targetIds);
        for (String s : array) {
            User user = userService.findUserByUserId(Long.valueOf(s));
            GroupMember member = new GroupMember();
            member.setCreateTime(groupDefine.getCreateTime());
            member.setGroupId(groupDefine.getGroupId());
            member.setUserId(user.getUserId());
            member.setMemberName(user.getName());
            members.add(member);
        }

        groupDao.insert(groupDefine, false);
        groupMemberDao.insertBatch(members, false);

        return groupDefine;

    }

    public List<Long> findMembersUserIdByGroupId(Long groupId) {
        List<Long> ret = groupStorage.getGroupMembers(groupId);
        if(ret == null || ret.size() == 0) {
            if(groupStorage.lockGroupMembers(groupId)) {
                try {
                    ret = groupDao.selectMemberUserIdByGroupId(groupId);
                    if(ret != null || ret.size() > 0) {
                        groupStorage.setGroupMembersWithID(groupId, ret);
                    }
                } finally {
                    groupStorage.unlockGroupMember(groupId);
                }
            }
        }
        return ret;
    }

    private String getDefaultGroupName(Long userId, String targetIds) {
        if(!targetIds.contains(",")) {
            return null;
        }

        User user = userService.findUserByUserId(userId);
        if(user == null) {
            throw new ApiException("没有此用户" + userId);
        }

        String[] targetIdArray = splitTargetIds(targetIds);
        User target = userService.findUserByUserId(Long.valueOf(targetIdArray[0]));
        if(target == null) {
            throw new ApiException("没有此用户" + userId);
        }
        return user.getName() + "、" + target.getName() + "等" + (targetIdArray.length + 1) + "人";
    }

    public List<GroupDefine> queryGroupList(Long userId, Integer pageNo, Integer pageSize) {
        return groupDao.queryGroupListByMemberId(userId, (pageNo - 1) * pageSize, pageSize);
    }

    public List<GroupMember> queryGroupMemberList(Long groupId, Integer pageNo, Integer pageSize) {
        return groupDao.queryGroupMemberListByGroupId(groupId, (pageNo - 1) * pageSize, pageSize);
    }

    private String[] splitTargetIds(String targetIds) {
        if(StringUtils.isBlank(targetIds)) {
            throw new ApiException("targetIds 参数不能为空");
        }

        if(!targetIds.contains(",")) {
            return new String[]{targetIds};
        }

        return targetIds.split(",");
    }
}
