package com.fb.activity.service;

import com.fb.activity.dto.ActivityBO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IActivityService {


    /**
     * 发布活动
     *
     * @param activityBO
     * @return
     */
    Optional<Long> publishActivity(ActivityBO activityBO);

    /**
     * 查询草稿
     *
     * @param userId
     * @return
     */
    Optional<ActivityBO> queryDraft(Long userId);

    /**
     * 查询详情
     *
     * @param
     * @return
     */
    Optional<ActivityBO> queryActivityById(Long activityId);

    /**
     * 查询活动列表
     * @param activityType
     * @param pageSize
     * @param pageNum
     * @return
     */
    Optional<List<ActivityBO>> queryActivityListByType(Integer activityType, Integer activityValid, int pageSize, int pageNum);

    /**
     * 根据日期查询活动列表
     * @param limit
     * @param offsetId
     * @return
     */
    Optional<List<ActivityBO>> queryActivityListByUid(List<Long> userIdList, int limit, Long offsetId);

    /**
     * 根据用户id查询活动列表
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    Optional<List<ActivityBO>> queryActivityListByUserId(Long userId, int pageSize, int pageNum);


    /**
     * 删除活动
     * @param userId
     * @param activityId
     * @return
     */
    boolean deleteActivity(Long activityId, Long userId);

    /**
     *
     * @param activityId
     * @param userId
     * @return
     */
    boolean stopActivity(Long activityId, Long userId);

    /**
     * 查询详情
     *
     * @param
     * @return
     */
    Optional<ActivityBO> queryActivityById(Long activityId, Long ticketId);

}
