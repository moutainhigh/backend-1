package com.fb.activity.service;

import com.fb.activity.dto.ActivityBO;

import java.util.Optional;

public interface IActivityService {


    /**
     * 发布活动
     * @param activityBO
     * @return
     */
    public Optional<Long> publishActivity(ActivityBO activityBO);

    /**
     * 查询草稿
     * @param userId
     * @return
     */
    public Optional<ActivityBO> queryDraft(Long userId);

}
