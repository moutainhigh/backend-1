package com.fb.web.service;

import com.fb.activity.dto.ActivityBO;
import com.fb.activity.dto.TicketBO;
import com.fb.activity.enums.ActivityTypeEnum;
import com.fb.activity.service.IActivityService;
import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import com.fb.common.util.DateUtils;
import com.fb.feed.dto.FeedBO;
import com.fb.web.entity.ActivityVO;
import com.fb.web.entity.TicketVO;
import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.output.ActivityListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityFacadeService {

    @Autowired
    private LbsMapService lbsMapService;
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

        Optional<LbsMapBo> lbsMapBo = lbsMapService.getLbsInfoByLocation(activityVo.getLocation());
        if (!lbsMapBo.isPresent()) {
            return Optional.empty();
        }
        activityBO.setCityCode(lbsMapBo.get().getCityCode());
        activityBO.setCityName(lbsMapBo.get().getCityName());
        activityBO.setAdName(lbsMapBo.get().getAdName());
        activityBO.setAdCode(lbsMapBo.get().getAdCode());
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

    /**
     * 查询详情
     *
     * @param activityId
     * @return
     */
    public Optional<ActivityDetailVO> queryActivityById(Long activityId) {
        //请求活动
        Optional<ActivityBO> activityBO = activityService.queryActivityById(activityId);
        //TODO LX 请求 用户信息
        //TODO LX 请求 IM信息
        if (activityBO.isPresent()) {

            return Optional.of(activityBOConvertToDetailVO(activityBO.get()));
        }
        return Optional.empty();
    }

    /**
     *
     * 活动列表查询分页
     * @param activityType
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Optional<List<ActivityListVO>> queryActivityListByType(int activityType, int pageSize, int pageNum) {
        //请求活动
        Optional<List<ActivityBO>> activityBO = activityService.queryActivityListByType(activityType, pageSize, pageNum);
        //TODO LX 请求 用户信息
        //TODO LX 请求 IM信息
        if (activityBO.isPresent()) {
            return Optional.of(activityBO.get().stream().map(activity -> activityBOConvertToListVO(activity)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    /**
     * 根据偏移量查询活动
     * @param limit
     * @param offsetId
     * @return
     */
    public Optional<List<ActivityDetailVO>> queryActivityListFollow(List<Long> userIdList, int limit, Long offsetId) {
        Optional<List<ActivityBO>> activityBO = activityService.queryActivityListByUid(userIdList, limit, offsetId);
        if (activityBO.isPresent()) {
            return Optional.of(activityBO.get().stream().map(activity -> activityBOConvertToDetailVO(activity)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }



    private ActivityListVO activityBOConvertToListVO(ActivityBO activityBO) {
        ActivityListVO activityListVO = new ActivityListVO();
//        activityListVO.setJoinCount();
//        activityListVO.setUserVO();
        if (!CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
            Optional<TicketBO> ticketBO = activityBO.getTicketBOList().stream().filter(Objects::nonNull).max(Comparator.comparing(TicketBO ::getAssemblePrice));
            if (ticketBO.isPresent()) {
                activityListVO.setAssemblePrice(ticketBO.get().getAssemblePrice());
                activityListVO.setAssembleMemberCount(ticketBO.get().getAssembleMemberCount());
            }
        }
        activityListVO.setId(activityBO.getId());
        activityListVO.setActivityTitle(activityBO.getActivityTitle());
        activityListVO.setPicUrl(activityBO.getPicUrl());
        activityListVO.setPrice(activityBO.getFrontMoney());
        activityListVO.setPublishTime(DateUtils.getDateFromLocalDateTime(activityBO.getUpdateTime(), DateUtils.dateTimeFormatterMin));
        activityListVO.setCityName(activityBO.getCityName());
        activityListVO.setActivityTime(String.valueOf(activityBO.getActivityTime()));
        activityListVO.setMemberCount(activityBO.getMemberCount());
        activityListVO.setActivityAddress(activityBO.getActivityAddress());

        return activityListVO;
    }

    private ActivityDetailVO activityBOConvertToDetailVO(ActivityBO activityBO) {
        ActivityDetailVO activityDetailVO = new ActivityDetailVO();
        activityDetailVO.setId(activityBO.getId());
//        activityDetailVO.setUserVO();
        activityDetailVO.setPublishTime(DateUtils.getDateFromLocalDateTime(activityBO.getUpdateTime(), DateUtils.dateTimeFormatterMin));
        activityDetailVO.setCityName(activityBO.getCityName());
        activityDetailVO.setActivityTime(String.valueOf(activityBO.getActivityTime()));
        activityDetailVO.setUserType(activityBO.getUserType());
        activityDetailVO.setFrontMoney(activityBO.getFrontMoney());
        activityDetailVO.setActivityTitle(activityBO.getActivityTitle());
        activityDetailVO.setMemberCount(activityBO.getMemberCount());
        activityDetailVO.setActivityValid(activityBO.getActivityValid());
        activityDetailVO.setEnrollEndTime(String.valueOf(activityBO.getEnrollEndTime()));
        activityDetailVO.setActivityAddress(activityBO.getActivityAddress());
        activityDetailVO.setActivityType(activityBO.getActivityType());
        activityDetailVO.setActivityTypeName(ActivityTypeEnum.getActivityTypeEnumByCode(activityBO.getActivityType()).getValue());
        activityDetailVO.setNeedInfo(activityBO.getNeedInfo());
        activityDetailVO.setRefundFlag(activityBO.getRefundFlag());
        activityDetailVO.setPicUrl(activityBO.getPicUrl());
        activityDetailVO.setVideoUrl(activityBO.getVideoUrl());
        activityDetailVO.setLocation(activityBO.getLocation());
        activityDetailVO.setContent(activityBO.getActivityContent());
//        activityDetailVO.setGroupId();
//        activityDetailVO.setPayGroupId();
        if (!CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
            activityDetailVO.setTicketVoList(activityBO.getTicketBOList().stream().map(ticketBO -> ticketBOConvertToVO(ticketBO)).collect(Collectors.toList()));
        }
        return activityDetailVO;
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
        activityVO.setActivityTypeName(ActivityTypeEnum.getValueByCode(activityBO.getActivityType()));
        activityVO.setNeedInfo(activityBO.getNeedInfo());
        activityVO.setRefundFlag(activityBO.getRefundFlag());
        activityVO.setState(activityBO.getActivityState());
        activityVO.setFrontMoney(activityBO.getFrontMoney());
        activityVO.setPicUrl(activityBO.getPicUrl());
        activityVO.setVideoUrl(activityBO.getVideoUrl());
        activityVO.setContent(activityBO.getActivityContent());
        activityVO.setLocation(activityBO.getLocation());
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
        activityBO.setLocation(activityVO.getLocation());

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
