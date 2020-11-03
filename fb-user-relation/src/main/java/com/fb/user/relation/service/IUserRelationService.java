package com.fb.user.relation.service;

import com.fb.user.relation.domian.DirectFriendRelation;

import java.util.List;

public interface IUserRelationService {

    /**
     * 添加好友
     * 1.写入mysql，记录直接好友关系（后续再增加权限设置）
     * 2.controller层判断是否同一个城市，同一个城市则进行间接好友关系维护，写图数据库
     * @param userId1
     * @param userId2
     */
    void addFriend(Long userId1, Long userId2);

    /**
     * 删除好友
     * 1.删除mysql，删除直接好友关系
     * 2.调用图数据库进行边删除
     * @param userId1
     * @param userId2
     */
    void removeFriend(Long userId1, Long userId2);

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
    boolean followUser(Long userId, Long followedUserId);

    /**
     * 取消关注
     * @param userId
     * @param followedUserId
     * @return
     */
    boolean unFollowUser(Long userId, Long followedUserId);

    //获取直接好友用户关系
    List<DirectFriendRelation> listDirectFriendRelation(Long userId);

    int countFriends(Long userId);


    /**
     * 可以先获取直接好友的动态和活动，1级 + 2级 + 3级。这边的分页是怎么做的呢
     * 获取同城的间接好友
     */
    List<Long> listSameCityAllFriends(Long userId);
}
