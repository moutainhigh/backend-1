package com.fb.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fb.activity.dao.ActivityBatchDao;
import com.fb.activity.dao.TicketBatchDao;
import com.fb.activity.dao.mapper.ActivityDao;
import com.fb.activity.dao.mapper.TicketDao;
import com.fb.activity.dto.ActivityBO;
import com.fb.activity.dto.TicketBO;
import com.fb.activity.entity.ActivityPO;
import com.fb.activity.entity.TicketPO;
import com.fb.activity.enums.ActivityStateEnum;
import com.fb.activity.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActivityBatchDao activityBatchDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private TicketBatchDao ticketBatchDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Long> publishActivity(ActivityBO activityBO) {
        ActivityPO activityPO = activityBOToPO(activityBO);

            if (activityBatchDao.saveOrUpdate(activityPO) && Objects.nonNull(activityPO.getId()) && !CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
                List<TicketPO> ticketPOList = activityBO.getTicketBOList().stream().map(ticketBO -> ticketBOToPO(ticketBO, activityPO.getId())).collect(Collectors.toList());
                ticketBatchDao.saveOrUpdateBatch(ticketPOList);
            }

        return Optional.ofNullable(activityPO.getId());
    }



    @Override
    public Optional<ActivityBO> queryDraft(Long userId) {

        ActivityPO activityPO = activityDao.findByUserIdAndState(userId, ActivityStateEnum.DRAFT.getCode());
        if (Objects.nonNull(activityPO)) {

            QueryWrapper<TicketPO> queryWrapper = new QueryWrapper<TicketPO>();
            queryWrapper.lambda().and(obj-> {
                obj.eq(TicketPO::getActivityId, activityPO.getId());
            }).orderByDesc(TicketPO::getId);

            List<TicketPO> ticketPOList = ticketDao.selectList(queryWrapper);
           return Optional.of(activityPOToBO(activityPO, ticketPOList));
        }
        return Optional.empty();
    }

    private ActivityBO activityPOToBO(ActivityPO activityPO, List<TicketPO> ticketPOList) {
        ActivityBO activityBO = new ActivityBO();
        activityBO.setId(activityPO.getId());
        activityBO.setUserId(activityPO.getUserId());
        activityBO.setUserType(activityPO.getUserType());
        activityBO.setActivityValid(activityPO.getActivityValid());
        activityBO.setActivityTitle(activityPO.getActivityTitle());
        activityBO.setMemberCount(activityPO.getMemberCount());
        activityBO.setActivityTime(activityPO.getActivityTime());
        activityBO.setEnrollEndTime(activityPO.getEnrollEndTime());
        activityBO.setActivityAddress(activityPO.getActivityAddress());
        activityBO.setCityCode(activityPO.getCityCode());
        activityBO.setAdCode(activityPO.getAdCode());
        activityBO.setActivityType(activityPO.getActivityType());
        activityBO.setNeedInfo(activityPO.getNeedInfo());
        activityBO.setRefundFlag(activityPO.getRefundFlag());
        activityBO.setActivityState(activityPO.getActivityState());
        activityBO.setFrontMoney(activityPO.getFrontMoney());
        activityBO.setPicUrl(activityPO.getPicUrl());
        activityBO.setVideoUrl(activityPO.getVideoUrl());
        activityBO.setActivityContent(activityPO.getActivityContent());
        if (!CollectionUtils.isEmpty(ticketPOList)) {
            activityBO.setTicketBOList(ticketPOList.stream().map(ticketPO -> ticketPOToBO(ticketPO)).collect(Collectors.toList()));
        }
        return activityBO;
    }
    private ActivityPO activityBOToPO(ActivityBO activityBO) {
        ActivityPO activityPO = new ActivityPO();
        activityPO.setId(activityBO.getId());
        activityPO.setUserId(activityBO.getUserId());
        activityPO.setUserType(activityBO.getUserType());
        activityPO.setActivityValid(activityBO.getActivityValid());
        activityPO.setActivityTitle(activityBO.getActivityTitle());
        activityPO.setMemberCount(activityBO.getMemberCount());
        activityPO.setActivityTime(activityBO.getActivityTime());
        activityPO.setEnrollEndTime(activityBO.getEnrollEndTime());
        activityPO.setActivityAddress(activityBO.getActivityAddress());
        activityPO.setCityCode(activityBO.getCityCode());
        activityPO.setAdCode(activityBO.getAdCode());
        activityPO.setActivityType(activityBO.getActivityType());
        activityPO.setNeedInfo(activityBO.getNeedInfo());
        activityPO.setRefundFlag(activityBO.getRefundFlag());
        activityPO.setActivityState(activityBO.getActivityState());
        activityPO.setFrontMoney(activityBO.getFrontMoney());
        activityPO.setPicUrl(activityBO.getPicUrl());
        activityPO.setVideoUrl(activityBO.getVideoUrl());
        activityPO.setActivityContent(activityBO.getActivityContent());
        activityPO.setCreateTime(new Date());
        activityPO.setUpdateTime(new Date());
        return activityPO;
    }
    private TicketPO ticketBOToPO(TicketBO ticketBO, Long activityId) {
        TicketPO ticketPO = new TicketPO();
        ticketPO.setId(ticketBO.getId());
        ticketPO.setActivityId(activityId);
        ticketPO.setTicketName(ticketBO.getTicketName());
        ticketPO.setTicketPrice(ticketBO.getTicketPrice());
        ticketPO.setAssemble(ticketBO.getAssemble());
        ticketPO.setAssemblePrice(ticketBO.getAssemblePrice());
        ticketPO.setAssembleMemberCount(ticketBO.getAssembleMemberCount());
        ticketPO.setTicketState(ticketBO.getTicketState());
        ticketPO.setIllustration(ticketBO.getIllustration());
        ticketPO.setCreateTime(new Date());
        ticketPO.setUpdateTime(new Date());
        return ticketPO;

    }
    private TicketBO ticketPOToBO(TicketPO activityPO) {
        TicketBO ticketBO = new TicketBO();
        ticketBO.setId(activityPO.getId());
        ticketBO.setTicketName(activityPO.getTicketName());
        ticketBO.setTicketPrice(activityPO.getTicketPrice());
        ticketBO.setAssemble(activityPO.getAssemble());
        ticketBO.setAssemblePrice(activityPO.getAssemblePrice());
        ticketBO.setAssembleMemberCount(activityPO.getAssembleMemberCount());
        ticketBO.setTicketState(activityPO.getTicketState());
        ticketBO.setIllustration(activityPO.getIllustration());
        return ticketBO;

    }
}
