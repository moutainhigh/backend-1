package com.fb.order.service;

import com.fb.order.dto.EnrollInfoBO;

import java.util.List;

public interface EnrollService {

    /**
     * 用户报名
     * @param
     * @return
     */
    boolean enrollActivity(Long userId, Long activityId, Long publishUserId);


    /**
     * 查询报名列表
     * @param userId
     * @param limit
     * @param offsetId
     * @return
     */
     List<EnrollInfoBO> queryEnrollList(Long userId, int limit, long offsetId);

}
