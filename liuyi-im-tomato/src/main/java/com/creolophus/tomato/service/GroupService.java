package com.creolophus.tomato.service;

import com.creolophus.im.common.entity.GroupDefine;
import com.creolophus.im.common.entity.GroupMember;
import com.creolophus.tomato.base.BaseService;
import com.creolophus.tomato.feign.BackendFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class GroupService extends BaseService {

    @Resource
    private BackendFeign backendFeign;

    public void appendMember(Long groupId, Long memberImId) {
        backendFeign.appendGroupMember(groupId, memberImId);
    }

    public GroupDefine createGroup(long imId, String targetIds) {
        return backendFeign.createGroup(imId, targetIds);
    }

    public List<GroupDefine> queryGroupList(Long imId, Integer pageNo, Integer pageSize) {
        return backendFeign.queryGroupList(imId, pageNo, pageSize);
    }

    public List<GroupMember> queryGroupMemberList(Long groupId, Integer pageNo, Integer pageSize) {
        return backendFeign.queryGroupMemberList(groupId, pageNo, pageSize);
    }
}
