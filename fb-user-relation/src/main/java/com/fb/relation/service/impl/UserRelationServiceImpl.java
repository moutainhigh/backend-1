package com.fb.relation.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.fb.relation.dao.FollowRelationDAO;
import com.fb.relation.dao.IDirectRelationDAO;
import com.fb.relation.dao.IIndirectRelationDao;
import com.fb.relation.domian.graph.UserGraphManager;
import com.fb.relation.repository.DirectRelationPO;
import com.fb.relation.repository.FollowRelationPO;
import com.fb.relation.service.DTO.UserDTOForRelation;
import com.fb.relation.domian.DirectFriendRelation;
import com.fb.relation.service.IUserRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: pangminpeng
 * @create: 2020-06-10 23:55
 */
@Service
public class UserRelationServiceImpl implements IUserRelationService {


    @Resource
    private IDirectRelationDAO directRelationDao;
    @Resource
    private IIndirectRelationDao indirectRelationDao;
    @Resource
    private FollowRelationDAO followRelationDAO;
    @Resource
    private UserGraphManager userGraphManager;


    @Override
    public void addFriend(UserDTOForRelation user1, UserDTOForRelation user2) {
        //更新两个人的直接好友关系
        if (Objects.isNull(user1) || Objects.isNull(user2)) {
            throw new RuntimeException("某用户不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        DirectRelationPO directRelationPO1 = new DirectRelationPO(user1.getUid(), user2.getUid(), now);
        DirectRelationPO directRelationPO2 = new DirectRelationPO(user2.getUid(), user1.getUid(), now);

//        if (user1.getUid() < user2.getUid()) {
//            directRelationPO = new DirectRelationPO(user1.getUid(), user2.getUid(), LocalDateTime.now());
//        } else {
//            directRelationPO = new DirectRelationPO(user2.getUid(), user1.getUid(), LocalDateTime.now());
//        }
        directRelationDao.batchInsert(Arrays.asList(directRelationPO1, directRelationPO2));
        if (Objects.equals(user1.getCityCode(), user2.getCityCode())) {
           userGraphManager.addRelation(user1.getUid(), user2.getUid(), user1.getCityCode());
        }
    }

    @Override
    public void removeFriend(UserDTOForRelation user1, UserDTOForRelation user2) {
        if (Objects.isNull(user1) || Objects.isNull(user2)) {
            throw new RuntimeException("某用户不存在");
        }
//        if (user1.getUid() < user2.getUid()) {
//            directRelationDao.deleteRelation(user1.getUid(), user2.getUid());
//        } else {
//            directRelationDao.deleteRelation(user2.getUid(), user1.getUid());
//        }
        directRelationDao.deleteRelation(new DirectRelationPO(user1.getUid(), user2.getUid()));
        // todo 可以异步
        if (Objects.equals(user1.getCityCode(), user2.getCityCode())) {
            userGraphManager.removeRelation(user1, user2);
        }
    }

    @Override
    public List<Long> getAllRelation(UserDTOForRelation user) {
        //获取所有的直接好友
        //获取所有的间接好友
        List<Long> directUserIdList = directRelationDao.listDirectFriendId(user.getUid());
        List<Long> list = userGraphManager.listIndirect(user);
        directUserIdList.addAll(list);
        return directUserIdList;
    }


    @Override
    public void followUser(Long userId, Long followUserId) {
        if (followRelationDAO.countByUidAndTargetUid(userId, followUserId) > 0) return;
        FollowRelationPO followRelationPO = new FollowRelationPO();
        followRelationPO.setUserId(userId);
        followRelationPO.setFollowedUserId(followUserId);
        followRelationPO.setCreateTime(LocalDateTime.now());
        followRelationDAO.insert(followRelationPO);
    }

    @Override
    public void unFollowUser(Long userId, Long followUserId) {
        FollowRelationPO followRelationPO = new FollowRelationPO();
        followRelationPO.setUserId(userId);
        followRelationPO.setFollowedUserId(followUserId);
        followRelationDAO.unfollow(userId, followUserId);
    }


    @Override
    public List<Long> listDirectFriends(Long userId) {
        return directRelationDao.listDirectFriendId(userId);
    }

    @Override
    public List<Long> listFollowUserId(Long userId) {
       return followRelationDAO.listFollowedUserId(userId);
    }

    @Override
    public List<Long> listFansUserId(Long userId) {
        return followRelationDAO.listFansId(userId);
    }

    @Override
    public List<DirectFriendRelation> listDirectFriendRelation(Long userId) {
        return null;
    }

    @Override
    public int countFriends(Long userId) {
        return 0;
    }

    @Override
    public List<Long> listSameCityAllFriends(Long userId) {
        return null;
    }

    @Override
    public List<Long> shortestPath(Long sourceUid, Long targetUid, String cityCode) {
        return userGraphManager.shortestPath(sourceUid, targetUid, cityCode);
    }
}
