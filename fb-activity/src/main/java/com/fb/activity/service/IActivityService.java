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
    Optional<List<ActivityBO>> queryActivityListByType(int activityType, int pageSize, int pageNum);

    /**
     * 根据日期查询活动列表
     * @param limit
     * @param offsetId
     * @return
     */
    Optional<List<ActivityBO>> queryActivityListByUid(List<Long> userIdList, int limit, Long offsetId);


}
