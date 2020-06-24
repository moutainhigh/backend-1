package com.fb.user.relation.service;

import com.fb.user.relation.domian.DirectFriendRelation;

import java.util.List;

public interface IUserRelationService {

    /**
     * 添加好友
     * @param userId1
     * @param userId2
     */
    void addFriend(Long userId1, Long userId2);

    /**
     * 删除好友
     * @param userId1
     * @param userId2
     */
    void deleteFriend(Long userId1, Long userId2);

    /**
     * 获取用户好友
     * @param userId
     * @return
     */
    List<Long> getFriends(Long userId);

    /**
     * 获取用户关注列表
     * @param userId
     * @return
     */
    List<Long> getFocuses(Long userId);

    /**
     * 获取用户被关注的列表
     */
    List<Long> getFouseds(Long userId);

    boolean focusUser(Long userId1, Long userId2);

    //获取用户通讯录
    List<DirectFriendRelation> listDirectFriendRelation(Long userId);


    /**
     * 可以先获取直接好友的动态和活动，1级 + 2级 + 3级。这边的分页是怎么做的呢
     */
}
