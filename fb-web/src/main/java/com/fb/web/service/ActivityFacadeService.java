package com.fb.web.service;

import com.fb.activity.dto.ActivityBO;
import com.fb.activity.dto.TicketBO;
import com.fb.activity.enums.ActivityTypeEnum;
import com.fb.activity.enums.ActivityValidEnum;
import com.fb.activity.service.IActivityService;
import com.fb.addition.dto.LikeBO;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.addition.service.ICommentService;
import com.fb.addition.service.ILikeService;
import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import com.fb.common.util.DateUtils;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IUserService;
import com.fb.web.entity.ActivityVO;
import com.fb.web.entity.TicketVO;
import com.fb.web.entity.UserVO;
import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.output.ActivityListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityFacadeService {

    @Autowired
    private LbsMapService lbsMapService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

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
     * 页面展示查询详情
     *
     * @param activityId
     * @return
     */
    public Optional<ActivityDetailVO> queryActivityById(Long activityId, Long uid) {
        //请求活动
        Optional<ActivityBO> activityBO = activityService.queryActivityById(activityId);
        Optional<List<LikeBO>> likeList = likeService.getLikeList(activityId, InfoTypeEnum.ACTIVITY);
        int commentCount = commentService.getCommentCount(activityId, InfoTypeEnum.ACTIVITY);
        //TODO LX 请求 IM信息
        if (activityBO.isPresent()) {
            UserDTO userDTO = userService.getUserByUid(activityBO.get().getUserId());
            return Optional.of(activityBOConvertToDetailVO(activityBO.get(), likeList, commentCount, uid, userDTO));
        }
        return Optional.empty();
    }

    /**
     * 查询活动详情
     * @param activityId
     * @return
     */
    public Optional<ActivityBO> queryActivityById(Long activityId) {
        return activityService.queryActivityById(activityId);
    }

    /**
     * 活动列表查询分页
     *
     * @param activityType
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Optional<List<ActivityListVO>> queryActivityListByType(Integer activityType, Integer activityValid, int pageSize, int pageNum) {
        //请求活动
        Optional<List<ActivityBO>> activityBO = activityService.queryActivityListByType(activityType, activityValid, pageSize, pageNum);

        if (activityBO.isPresent()) {
            return Optional.of(activityBO.get().stream().filter(activityBO1->{
                return activityBO1.getActivityTime().after(new Date());
            }).map(activity -> {
                UserDTO userDTO = userService.getUserByUid(activity.getUserId());
               return activityBOConvertToListVO(activity, userDTO);
            }).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    /**
     * 获取个人活动列表
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Optional<List<ActivityDetailVO>> queryActivityListByUserId(Long userId, int pageSize, int pageNum) {
        //请求活动
        Optional<List<ActivityBO>> activityBO = activityService.queryActivityListByUserId(userId, pageSize, pageNum);

        if (activityBO.isPresent()) {
            return Optional.of(activityBO.get().stream().map(activity -> {
                Optional<List<LikeBO>> likeList = likeService.getLikeList(activity.getId(), InfoTypeEnum.ACTIVITY);
                int commentCount = commentService.getCommentCount(activity.getId(), InfoTypeEnum.ACTIVITY);
                UserDTO userDTO = userService.getUserByUid(activity.getUserId());
                return activityBOConvertToDetailVO(activity, likeList, commentCount, userId, userDTO);
            }).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    /**
     * 删除活动
     *
     * @param userId
     * @param activityId
     * @return
     */
    public boolean deleteActivity(Long activityId, Long userId) {
        boolean result = activityService.deleteActivity(activityId, userId);
        return result;
    }

    /**
     * 停止报名
     * @param activityId
     * @param userId
     * @return
     */
    public boolean stopActivity(Long activityId, Long userId) {
        boolean result = activityService.stopActivity(activityId, userId);
        return result;
    }

    /**
     * 根据偏移量查询活动
     *
     * @param limit
     * @param offsetId
     * @return
     */
    public Optional<List<ActivityDetailVO>> queryActivityListFollow(List<Long> userIdList, int limit, Long offsetId, Long userId) {
        Optional<List<ActivityBO>> activityBO = activityService.queryActivityListByUid(userIdList, limit, offsetId);
        if (activityBO.isPresent()) {
            return Optional.of(activityBO.get().stream().map(activity -> {
                Optional<List<LikeBO>> likeList = likeService.getLikeList(activity.getId(), InfoTypeEnum.ACTIVITY);
                int commentCount = commentService.getCommentCount(activity.getId(), InfoTypeEnum.ACTIVITY);
                UserDTO userDTO = userService.getUserByUid(activity.getUserId());
                return activityBOConvertToDetailVO(activity, likeList, commentCount, userId, userDTO);
            }).collect(Collectors.toList()));
        }
        return Optional.empty();
    }


    private ActivityListVO activityBOConvertToListVO(ActivityBO activityBO, UserDTO userDTO) {
        ActivityListVO activityListVO = new ActivityListVO();
        //FIXME IM参加人数
//        activityListVO.setJoinCount();
        activityListVO.setUserVO(userDTOConvertUserVO(userDTO));
        activityListVO.setPrice(activityBO.getFrontMoney());

        if (!CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
            Optional<TicketBO> ticketBO = activityBO.getTicketBOList().stream().filter(Objects::nonNull).min(Comparator.comparing(TicketBO::getAssemblePrice));
            if (ticketBO.isPresent()) {
                activityListVO.setPrice(ticketBO.get().getTicketPrice());
                activityListVO.setAssemblePrice(ticketBO.get().getAssemblePrice());
                activityListVO.setAssembleMemberCount(ticketBO.get().getAssembleMemberCount());
            }
        }
        activityListVO.setId(activityBO.getId());
        activityListVO.setActivityTitle(activityBO.getActivityTitle());
        activityListVO.setPicUrl(Objects.isNull(activityBO.getPicUrl()) ? "" : activityBO.getPicUrl());
        activityListVO.setPublishTime(DateUtils.getDateFromLocalDateTime(activityBO.getUpdateTime(), DateUtils.dateTimeFormatterMin));
        activityListVO.setCityName(activityBO.getCityName());
        activityListVO.setActivityTime(String.valueOf(activityBO.getActivityTime()));
        activityListVO.setMemberCount(activityBO.getMemberCount());
        activityListVO.setActivityAddress(activityBO.getActivityAddress());

        return activityListVO;
    }
    private UserVO userDTOConvertUserVO(UserDTO userDTO) {
        UserVO userVO = new UserVO();
        userVO.setUserId(userDTO.getUid());
        userVO.setUserName(userDTO.getName());
        userVO.setPic(userDTO.getHeadPicUrl());
//        userVO.setOrder(0);
        return userVO;

    }

    private ActivityDetailVO activityBOConvertToDetailVO(ActivityBO activityBO, Optional<List<LikeBO>> likeList, int commentNum, Long uid,UserDTO userDTO) {
        ActivityDetailVO activityDetailVO = new ActivityDetailVO();
        activityDetailVO.setId(activityBO.getId());

        activityDetailVO.setUserVO(userDTOConvertUserVO(userDTO));
        activityDetailVO.setPublishTime(DateUtils.getDateFromLocalDateTime(activityBO.getUpdateTime(), DateUtils.dateTimeFormatterMin));
        activityDetailVO.setCityName(activityBO.getCityName());
        activityDetailVO.setActivityTime(String.valueOf(activityBO.getActivityTime()));
        activityDetailVO.setUserType(activityBO.getUserType());
        activityDetailVO.setFrontMoney(activityBO.getFrontMoney());
        activityDetailVO.setActivityTitle(activityBO.getActivityTitle());
        activityDetailVO.setMemberCount(activityBO.getMemberCount());
        activityDetailVO.setActivityValid(activityBO.getActivityValid());
        activityDetailVO.setActivityStatus(activityBO.getActivityState());
        activityDetailVO.setEnrollEndTime(String.valueOf(activityBO.getEnrollEndTime()));
        activityDetailVO.setActivityAddress(activityBO.getActivityAddress());
        activityDetailVO.setActivityType(activityBO.getActivityType());
        activityDetailVO.setUserType(activityBO.getUserType());
        if (Objects.nonNull(activityBO.getActivityType()) && Objects.nonNull(ActivityTypeEnum.getActivityTypeEnumByCode(activityBO.getActivityType()))) {
            String activityTypeName = ActivityTypeEnum.getActivityTypeEnumByCode(activityBO.getActivityType()).getValue();
            activityDetailVO.setActivityTypeName(StringUtils.isEmpty(activityTypeName) ? "" : activityTypeName);
        }
        activityDetailVO.setNeedInfo(activityBO.getNeedInfo());
        activityDetailVO.setRefundFlag(activityBO.getRefundFlag());
        activityDetailVO.setPicUrl(Objects.isNull(activityBO.getPicUrl())? "": activityBO.getPicUrl());
        activityDetailVO.setVideoUrl(Objects.isNull(activityBO.getVideoUrl())? "": activityBO.getVideoUrl());
        activityDetailVO.setLocation(activityBO.getLocation());
        activityDetailVO.setContent(activityBO.getActivityContent());
        //TODO LX 请求 IM信息
//        activityDetailVO.setGroupId();
//        activityDetailVO.setPayGroupId();
        if (!CollectionUtils.isEmpty(activityBO.getTicketBOList())) {
            activityDetailVO.setTicketVoList(activityBO.getTicketBOList().stream().map(ticketBO -> ticketBOConvertToVO(ticketBO)).collect(Collectors.toList()));
        }
        activityDetailVO.setLikeNum(0);
        if (likeList.isPresent()) {
            activityDetailVO.setLikeNum(likeList.get().size());
            activityDetailVO.setLike(likeList.get().stream().anyMatch(likeBO -> likeBO.getUserId().equals(uid)));
        }
        activityDetailVO.setCommentNum(commentNum);

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
        activityVO.setNeedInfo(activityBO.getNeedInfo());
        if (Objects.nonNull(activityBO.getActivityType())) {
            activityVO.setActivityTypeName(ActivityTypeEnum.getValueByCode(activityBO.getActivityType()));
        }
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
        if (StringUtils.isEmpty(activityVO.getActivityTime())) {
            activityBO.setActivityValid(ActivityValidEnum.LONG.getCode());
        } else {
            activityBO.setActivityValid(ActivityValidEnum.SHORT.getCode());
        }
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
