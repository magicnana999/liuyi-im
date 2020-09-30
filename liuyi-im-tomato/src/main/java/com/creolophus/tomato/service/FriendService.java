package com.creolophus.tomato.service;

import com.creolophus.im.common.entity.TomatoFriend;
import com.creolophus.tomato.base.BaseService;
import com.creolophus.tomato.dao.TomatoFriendDao;
import com.creolophus.tomato.feign.BackendFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class FriendService extends BaseService {

    @Resource
    private BackendFeign backendFeign;

    @Resource
    private TomatoFriendDao tomatoFriendDao;


    public Long createFriend(Long userId, Long targetId) {
        TomatoFriend friend = new TomatoFriend();
        friend.setLeftId(userId);
        friend.setRightId(targetId);
        friend.setCreateTime(new Date());
        tomatoFriendDao.insertTemplate(friend,true);
        return friend.getFriendId();
    }

    public List<TomatoFriend> queryFriendList(Long userId,Integer start,Integer end){
        return tomatoFriendDao.createLambdaQuery().andEq(TomatoFriend::getLeftId,userId).desc(TomatoFriend::getCreateTime).limit(start,end).select();
    }
}
