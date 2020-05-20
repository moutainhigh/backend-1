package com.fb.web.service;

import com.fb.activity.dto.ActivityBO;
import com.fb.activity.dto.TicketBO;
import com.fb.activity.service.IActivityService;
import com.fb.common.util.DateUtils;
import com.fb.web.entity.ActivityVO;
import com.fb.web.entity.TicketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityFacadeService {

    @Autowired
    private IActivityService activityService;

    /**
     * 发布活动
     *
     * @param activityVo
     * @return
     */
    public Optional<Long> publishActivity(ActivityVO activityVo, Long userId) {
        ActivityBO activityBO = activityVOConvertToBO(activityVo, userId);
        //TODO LX 获取地图服务
        activityBO.setCityCode(111);
        activityBO.setAdCode(222);

        return activityService.publishActivity(activityBO);

    }

    /**
     * 查询草稿
     *
     * @param userId
     * @return
     */
    public Optional<ActivityVO> queryDraft(Long userId) {

        Optional<ActivityBO> activityBO = activityService.queryDraft(userId);
        if (activityBO.isPresent()) {
            return Optional.of(activityBOConvertToVO(activityBO.get()));
        }
        return Optional.empty();
    }


    private ActivityVO activityBOConvertToVO(ActivityBO activityBO) {
        ActivityVO activityVO = new ActivityVO();
        activityVO.setId(activityBO.getId());
        activityVO.setUserId(activityBO.getUserId());
        activityVO.setUserType(activityBO.getUserType());
        activityVO.setActivityTitle(activityBO.getActivityTitle());
        activityVO.setMemberCount(activityBO.getMemberCount());
        activityVO.setActivityValid(activityBO.getActivityValid());
        activityVO.setActivityTime(DateUtils.getDateFromLocalDateTime(activityBO.getActivityTime(), DateUtils.dateTimeFormatterMin));
        activityVO.setEnrollEndTime(DateUtils.getDateFromLocalDateTime(activityBO.getEnrollEndTime(), DateUtils.dateTimeFormatterMin));
        activityVO.setActivityAddress(activityBO.getActivityAddress());
        activityVO.setActivityType(activityBO.getActivityType());
//        activityVO.setActivityTypeName();
        activityVO.setNeedInfo(activityBO.getNeedInfo());
        activityVO.setRefundFlag(activityBO.getRefundFlag());
        activityVO.setState(activityBO.getActivityState());
        activityVO.setFrontMoney(activityBO.getFrontMoney());
        activityVO.setPicUrl(activityBO.getPicUrl());
        activityVO.setVideoUrl(activityBO.getVideoUrl());
        activityVO.setContent(activityBO.getActivityContent());
//        activityVO.setLocation();
        if (!CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
            activityVO.setTicketVoList(activityBO.getTicketBOList().stream().map(ticketBO -> ticketBOConvertToVO(ticketBO)).collect(Collectors.toList()));
        }
        return activityVO;

    }

    private TicketVO ticketBOConvertToVO(TicketBO ticketBO) {
        TicketVO ticketVO = new TicketVO();
        ticketVO.setId(ticketBO.getId());
        ticketVO.setTicketName(ticketBO.getTicketName());
        ticketVO.setPrice(ticketBO.getAssemblePrice());
        ticketVO.setAssemble(ticketBO.getAssemble());
        ticketVO.setTicketState(ticketBO.getTicketState());
        ticketVO.setAssemblePrice(ticketBO.getAssemblePrice());
        ticketVO.setAssembleMemberCount(ticketBO.getAssembleMemberCount());
        ticketVO.setIllustration(ticketBO.getIllustration());
        return ticketVO;

    }


    private ActivityBO activityVOConvertToBO(ActivityVO activityVO, Long userId) {
        ActivityBO activityBO = new ActivityBO();
        activityBO.setId(activityVO.getId());
        activityBO.setUserId(userId);
        activityBO.setUserType(activityVO.getUserType());
        activityBO.setActivityValid(activityVO.getActivityValid());
        activityBO.setActivityTitle(activityVO.getActivityTitle());
        activityBO.setMemberCount(activityVO.getMemberCount());
        activityBO.setActivityTime(DateUtils.getDateFromLocalDateTime(activityVO.getActivityTime(), DateUtils.dateTimeFormatterMin));
        activityBO.setEnrollEndTime(DateUtils.getDateFromLocalDateTime(activityVO.getEnrollEndTime(), DateUtils.dateTimeFormatterMin));
        activityBO.setActivityAddress(activityVO.getActivityAddress());
        activityBO.setActivityType(activityVO.getActivityType());
        activityBO.setNeedInfo(activityVO.getNeedInfo());
        activityBO.setRefundFlag(activityVO.getRefundFlag());
        activityBO.setActivityState(activityVO.getState());
        activityBO.setFrontMoney(activityVO.getFrontMoney());
        activityBO.setPicUrl(activityVO.getPicUrl());
        activityBO.setVideoUrl(activityVO.getVideoUrl());
        activityBO.setActivityContent(activityVO.getContent());
        if (!CollectionUtils.isEmpty(activityVO.getTicketVoList())) {
            activityBO.setTicketBOList(activityVO.getTicketVoList().stream().map(ticketVO -> ticketVOConvertToBO(ticketVO)).collect(Collectors.toList()));
        }
        return activityBO;
    }

    private TicketBO ticketVOConvertToBO(TicketVO ticketVO) {
        TicketBO ticketBO = new TicketBO();
        ticketBO.setId(ticketVO.getId());
        ticketBO.setTicketName(ticketVO.getTicketName());
        ticketBO.setTicketPrice(ticketVO.getPrice());
        ticketBO.setAssemble(ticketVO.getAssemble());
        ticketBO.setAssemblePrice(ticketVO.getAssemblePrice());
        ticketBO.setAssembleMemberCount(ticketVO.getAssembleMemberCount());
        ticketBO.setTicketState(ticketVO.getTicketState());
        ticketBO.setIllustration(ticketVO.getIllustration());
        return ticketBO;

    }

}
