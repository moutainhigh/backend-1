package com.fb.relation.service;

import com.fb.relation.service.DTO.UserDTOForRelation;
import com.fb.relation.domian.DirectFriendRelation;

import java.util.List;

public interface IUserRelationService {

    /**
     * 添加好友
     * 1.写入mysql，记录直接好友关系（后续再增加权限设置）
     * 2.controller层判断是否同一个城市，同一个城市则进行间接好友关系维护，写图数据库
     * @param userId1
     * @param userId2
     */
    void addFriend(UserDTOForRelation user1, UserDTOForRelation user2);


    /**
     * 删除好友
     * 1.删除mysql，删除直接好友关系
     * 2.调用图数据库进行边删除
     * @param userId1
     * @param userId2
     */
    void removeFriend(UserDTOForRelation user1, UserDTOForRelation user2);

    /**
     * 获取用户的所有直接好友，用于用户通讯录
     * @param userId
     * @return
     */
    List<Long> listDirectFriends(Long userId);

    /**
     * 获取用户的关注列表
     * @param userId
     * @return
     */
    List<Long> listFollowUserId(Long userId);

    /**
     * 获取用户被关注的列表
     */
    List<Long> listFansUserId(Long userId);

    /**
     * 用户关注
     * @param userId 发起关注的用户id
     * @param followedUserId 被关注的用户id
     * @return
     */
    void followUser(Long userId, Long followedUserId);

    /**
     * 取消关注
     * @param userId
     * @param followedUserId
     * @return
     */
    void unFollowUser(Long userId, Long followedUserId);

    List<Long> getAllRelation(UserDTOForRelation user);


        //获取直接好友用户关系
    List<DirectFriendRelation> listDirectFriendRelation(Long userId);

    int countFriends(Long userId);


    /**
     * 可以先获取直接好友的动态和活动，1级 + 2级 + 3级。这边的分页是怎么做的呢
     * 获取同城的间接好友
     */
    List<Long> listSameCityAllFriends(Long userId);

    List<Long> shortestPath(Long sourceUid, Long targetUid, String cityCode);
}
