package com.fb.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.activity.dao.ActivityDAO;
import com.fb.activity.dao.ActivityBatchDAO;
import com.fb.activity.dao.TicketBatchDAO;
import com.fb.activity.dao.TicketDAO;
import com.fb.activity.dto.ActivityBO;
import com.fb.activity.dto.TicketBO;
import com.fb.activity.entity.ActivityPO;
import com.fb.activity.entity.TicketPO;
import com.fb.activity.enums.ActivityStateEnum;
import com.fb.activity.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    private ActivityDAO activityDao;

    @Autowired
    private ActivityBatchDAO activityBatchDao;

    @Autowired
    private TicketDAO ticketDao;

    @Autowired
    private TicketBatchDAO ticketBatchDao;


    /**
     * 发布活动
     *
     * @param activityBO
     * @return
     */
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


    /**
     * 查询草稿
     *
     * @param userId
     * @return
     */
    @Override
    public Optional<ActivityBO> queryDraft(Long userId) {

        ActivityPO activityPO = activityDao.findByUserIdAndState(userId, ActivityStateEnum.DRAFT.getCode());
        if (Objects.nonNull(activityPO)) {
            return Optional.of(activityPOToBO(activityPO, getTicketByActivityId(activityPO.getId())));
        }
        return Optional.empty();
    }

    /**
     * 查询活动详情
     *
     * @param activityId
     * @return
     */
    @Override
    public Optional<ActivityBO> queryActivityById(Long activityId) {
        ActivityPO activityPO = activityDao.selectById(activityId);
        if (Objects.nonNull(activityPO)) {
            return Optional.of(activityPOToBO(activityPO, getTicketByActivityId(activityPO.getId())));
        }
        return Optional.empty();
    }


    /**
     * 按照活动类型分页查询
     *
     * @param activityType
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public Optional<List<ActivityBO>> queryActivityListByType(int activityType, int pageSize, int pageNum) {
        List<ActivityBO> activityBOS = new ArrayList<>(pageSize);
        QueryWrapper<ActivityPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            if (activityType > 0) {
                obj.eq(ActivityPO::getActivityType, activityType);
            }
            obj.eq(ActivityPO::getUserType, 0);
        }).orderByDesc(ActivityPO::getUpdateTime);

        IPage<ActivityPO> activityList = activityDao.selectPage(new Page(pageNum, pageSize), queryWrapper);
        if (!CollectionUtils.isEmpty(activityList.getRecords())) {
            Map<Long, List<TicketPO>> activityMap = getTicketByActivityIds(activityList.getRecords().stream().map(ActivityPO::getId).collect(Collectors.toList()));
            activityList.getRecords().forEach(activityPO -> {
                activityBOS.add(activityPOToBO(activityPO, activityMap.get(activityPO.getId())));
            });
        }
        return Optional.ofNullable(activityBOS);
    }

    /**
     * 按照日期和人分页查询
     *
     * @param limit
     * @param offsetId
     * @return
     */
    @Override
    public Optional<List<ActivityBO>> queryActivityListByUid(List<Long> userIdList, int limit, Long offsetId) {
        List<ActivityBO> activityBOS = new ArrayList<>(limit);
        QueryWrapper<ActivityPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
//            obj.apply("date_format(gmt_creat,'%Y-%m-%d') = '"+date+"'");
            if (Objects.nonNull(offsetId) && offsetId > 0) {
                obj.lt(ActivityPO::getId, offsetId);
            }
            if (!CollectionUtils.isEmpty(userIdList)) {
                obj.in(ActivityPO::getUserId, userIdList);
            }
            obj.eq(ActivityPO::getActivityState, ActivityStateEnum.PUBLISH.getCode());
        }).orderByDesc(ActivityPO::getUpdateTime);

        IPage<ActivityPO> activityList = activityDao.selectPage(new Page(1, limit), queryWrapper);
        if (!CollectionUtils.isEmpty(activityList.getRecords())) {
            activityList.getRecords().forEach(activityPO -> {
                activityBOS.add(activityPOToBO(activityPO, null));
            });
        }
        return Optional.ofNullable(activityBOS);
    }

    /**
     * 根据用户id查询用户发布的活动列表
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public Optional<List<ActivityBO>> queryActivityListByUserId(Long userId, int pageSize, int pageNum) {
        List<ActivityBO> activityBOS = new ArrayList<>(pageSize);
        QueryWrapper<ActivityPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(ActivityPO::getUserId, userId);
            obj.in(ActivityPO::getActivityState, Arrays.asList(ActivityStateEnum.DRAFT.getCode(), ActivityStateEnum.PUBLISH.getCode()));
        }).orderByDesc(ActivityPO::getUpdateTime);

        IPage<ActivityPO> activityList = activityDao.selectPage(new Page(pageNum, pageSize), queryWrapper);
        if (!CollectionUtils.isEmpty(activityList.getRecords())) {
            Map<Long, List<TicketPO>> activityMap = getTicketByActivityIds(activityList.getRecords().stream().map(ActivityPO::getId).collect(Collectors.toList()));
            activityList.getRecords().forEach(activityPO -> {
                activityBOS.add(activityPOToBO(activityPO, activityMap.get(activityPO.getId())));
            });
        }
        return Optional.ofNullable(activityBOS);
    }

    @Override
    public boolean deleteActivity(Long activityId, Long userId) {

        QueryWrapper<ActivityPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(ActivityPO::getId, activityId);
        });

        ActivityPO activityPO = activityDao.selectOne(queryWrapper);
        if (Objects.nonNull(activityPO) && activityPO.getUserId().equals(userId)) {
            activityPO.setActivityState(ActivityStateEnum.DELETE.getCode());
            activityDao.updateById(activityPO);
        }
        return true;
    }


    private List<TicketPO> getTicketByActivityId(Long activityId) {
        QueryWrapper<TicketPO> queryWrapper = new QueryWrapper<TicketPO>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(TicketPO::getActivityId, activityId);
        }).orderByDesc(TicketPO::getId);

        return ticketDao.selectList(queryWrapper);
    }

    private Map<Long, List<TicketPO>> getTicketByActivityIds(List<Long> activityIdList) {
        QueryWrapper<TicketPO> queryWrapper = new QueryWrapper<TicketPO>();
        queryWrapper.lambda().and(obj -> {
            obj.in(TicketPO::getActivityId, activityIdList);
        });
        List<TicketPO> ticketPOList = ticketDao.selectList(queryWrapper);
        return ticketPOList.stream().collect(Collectors.groupingBy(TicketPO::getActivityId));
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
        activityBO.setCityName(activityPO.getCityName());
        activityBO.setAdName(activityPO.getAdName());
        activityBO.setLocation(activityPO.getLocation());
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
        activityBO.setUpdateTime(activityPO.getUpdateTime());

        return activityBO;
    }

    private ActivityPO activityBOToPO(ActivityBO activityBO) {
        ActivityPO activityPO = new ActivityPO();
        BeanUtils.copyProperties(activityBO, activityPO);

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
