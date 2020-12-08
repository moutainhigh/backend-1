package com.fb.web.service;

import com.fb.activity.dto.ActivityBO;
import com.fb.activity.service.IActivityService;
import com.fb.common.util.DateUtils;
import com.fb.order.dto.EnrollInfoBO;
import com.fb.order.service.EnrollService;
import com.fb.web.entity.EnrollVO;
import com.fb.web.entity.output.UserOrderInfoVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnrollFacadeService {

    @Autowired
    private EnrollService enrollService;

    @Autowired
    private IActivityService activityService;
    /**
     * 活动报名
     * @param userId
     * @param activityId
     * @param publisUserId
     * @return
     */
    public boolean enrollActivity(Long userId, Long activityId, Long publisUserId) {
       return enrollService.enrollActivity(userId, activityId, publisUserId);
    }


    /**
     * 查询报名列表
     * @param userId
     * @param limit
     * @param offsetId
     * @return
     */
    public List<EnrollVO> queryEnrollList(Long userId, int limit, long offsetId) {
        List<EnrollVO> enrollVOList = Lists.newArrayListWithExpectedSize(limit);
        List<EnrollInfoBO> enrollInfoBOList = enrollService.queryEnrollList(userId, limit, offsetId);
        if (!CollectionUtils.isEmpty(enrollInfoBOList)) {
            enrollInfoBOList.forEach(enrollInfoBO -> {
               Optional<ActivityBO> activityBO = activityService.queryActivityById(enrollInfoBO.getActivityId());
               if (activityBO.isPresent()) {
                   enrollVOList.add(convertBOToVO(activityBO.get(), enrollInfoBO.getId()));
               };
           });

        }
        return enrollVOList;
    }


    private EnrollVO convertBOToVO(ActivityBO activityBO, Long id) {
        EnrollVO enrollVO = new EnrollVO();
        enrollVO.setId(id);
        enrollVO.setActivityId(activityBO.getId());
        enrollVO.setActivityName(activityBO.getActivityTitle());
        enrollVO.setActivityTime(activityBO.getActivityTime());
        enrollVO.setAddress(activityBO.getActivityAddress());
        return enrollVO;

    }


}
