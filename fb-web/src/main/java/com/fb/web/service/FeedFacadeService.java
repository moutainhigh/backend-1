package com.fb.web.service;

import com.fb.addition.dto.LikeBO;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.addition.service.ICommentService;
import com.fb.addition.service.ILikeService;
import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import com.fb.feed.dto.FeedBO;
import com.fb.feed.enums.FeedStateEnum;
import com.fb.feed.service.IFeedService;
import com.fb.web.entity.FeedVO;
import com.fb.web.entity.output.FeedDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeedFacadeService {

    @Autowired
    private IFeedService feedService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private LbsMapService lbsMapService;

    /**
     * 发布动态
     *
     * @param feedVO
     * @param userId
     * @return
     */
    public Optional<Long> publishFeed(FeedVO feedVO, Long userId) {

        FeedBO feedBO = voToConvertBO(feedVO, userId);
        if (StringUtils.isNotEmpty(feedVO.getLocation())) {
            Optional<LbsMapBo> lbsMapBo = lbsMapService.getLbsInfoByLocation(feedVO.getLocation());
            feedBO.setCityCode(lbsMapBo.get().getCityCode());
            feedBO.setCityName(lbsMapBo.get().getCityName());
            feedBO.setAdName(lbsMapBo.get().getAdName());
            feedBO.setAdCode(lbsMapBo.get().getAdCode());
        }
        return feedService.publishFeed(feedBO);
    }

    /**
     * 获取动态详情
     *
     * @param feedId
     * @return
     */
    public Optional<FeedDetailVO> getFeedDetailById(Long feedId, Long uid) {
        Optional<FeedBO> feedBO = feedService.getFeedById(feedId);
        if (feedBO.isPresent()) {
            Optional<List<LikeBO>> likeList = likeService.getLikeList(feedId, InfoTypeEnum.FEED);
            int commentCount = commentService.getCommentCount(feedId, InfoTypeEnum.FEED);
            return Optional.ofNullable(boToConvertVO(feedBO.get(), likeList, commentCount, uid));
        }
        return Optional.empty();
    }

    /**
     * 获取好友关注动态列表
     *
     * @param userIdList
     * @param limit
     * @param offsetId
     * @return
     */
    public Optional<List<FeedDetailVO>> queryFeedListFollow(List<Long> userIdList, int limit, Long offsetId, Long uid) {
        Optional<List<FeedBO>> feedDetailVOS = feedService.queryFeedListByUid(userIdList, limit, offsetId);
        return getFeedAndAddition(feedDetailVOS, uid);
    }

    /**
     * 获取用户动态列表
     *
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Optional<List<FeedDetailVO>> queryFeedListByUserId(Long userId, int pageSize, int pageNum) {
        Optional<List<FeedBO>> feedDetailVOS = feedService.queryFeedListByUserId(userId, pageSize, pageNum);
        return getFeedAndAddition(feedDetailVOS, userId);
    }

    /**
     * 删除动态
     *
     * @param userId
     * @param feedId
     * @return
     */
    public boolean deleteFeed(Long feedId, Long userId) {
        boolean result = feedService.deleteFeed(feedId, userId);
        return result;
    }

    /**
     * 获取同城动态（分组乱序）
     *
     * @param location
     * @param limit
     * @param offsetId
     * @param random
     * @return
     */
    public Optional<List<FeedDetailVO>> getLocationFeedList(String location, int limit, Long offsetId, Integer random, Long uid) {
//        Optional<LbsMapBo> lbsMapBo = lbsMapService.getLbsInfoByLocation(location);

        Optional<List<FeedBO>> feedDetailVOS = feedService.queryLocationFeedList(/*lbsMapBo.get().getCityCode()*/location, limit, offsetId, random);

        return getFeedAndAddition(feedDetailVOS, uid);
    }

    private Optional<List<FeedDetailVO>> getFeedAndAddition(Optional<List<FeedBO>> feedDetailVOS, Long uid) {

        if (feedDetailVOS.isPresent() && !CollectionUtils.isEmpty(feedDetailVOS.get())) {
            List<Long> infoIdList = feedDetailVOS.get().stream().map(FeedBO::getId).collect(Collectors.toList());

            Optional<List<LikeBO>> likeList = likeService.getLikeListByInfoList(infoIdList, InfoTypeEnum.FEED);
            Map<Long, List<LikeBO>> likeMap = new HashMap<>();

            if (likeList.isPresent()) {
                likeMap = likeList.get().stream().collect(Collectors.groupingBy(LikeBO::getInfoId));
            }
            Optional<Map<Long, Long>> commentMapResult = commentService.getCommentCountMap(infoIdList, InfoTypeEnum.FEED);

            Map<Long, List<LikeBO>> finalLikeMap = likeMap;

            return Optional.of(feedDetailVOS.get().stream().map(feedBO -> {
                int commentCount = 0;
                if (commentMapResult.isPresent()) {
                    Long count = commentMapResult.get().get(feedBO.getId());
                    commentCount = count == null ? 0 : count.intValue();
                }
                return boToConvertVO(feedBO, Optional.ofNullable(finalLikeMap.get(feedBO.getId())), commentCount, uid);
            }).collect(Collectors.toList()));
        }

        return Optional.empty();
    }


    private FeedDetailVO boToConvertVO(FeedBO feedBO, Optional<List<LikeBO>> likeList, int commentNum, Long uid) {
        FeedDetailVO feedDetailVO = new FeedDetailVO();
//        feedDetailVO.setUserVO();
        feedDetailVO.setPublishTime(feedBO.getCreateTime().getTime());
        feedDetailVO.setCityName(feedBO.getCityName());
        feedDetailVO.setContent(feedBO.getFeedContent());
        feedDetailVO.setPicUrl(feedBO.getPicUrl());
        feedDetailVO.setVideoUrl(feedBO.getVideoUrl());
        feedDetailVO.setClockIn(feedBO.getClockIn());
        feedDetailVO.setClockInTag(feedBO.getClockInTag());
        feedDetailVO.setLocation(feedBO.getLocation());
        feedDetailVO.setId(feedBO.getId());

        feedDetailVO.setLikeNum(likeList.isPresent() ? likeList.get().size() : 0);
        feedDetailVO.setLike(false);
        if (likeList.isPresent() && Objects.nonNull(uid)) {
            feedDetailVO.setLike(likeList.get().stream().anyMatch(likeBO -> likeBO.getUserId().equals(uid)));
        }
        feedDetailVO.setCommentNum(commentNum);
        return feedDetailVO;

    }

    private FeedBO voToConvertBO(FeedVO feedVO, Long userId) {
        FeedBO feedBO = new FeedBO();
        feedBO.setClockIn(feedVO.getClockIn());
        feedBO.setClockInTag(feedVO.getClockInTag());
        feedBO.setDisplayCity(feedVO.getDisplayCity());
        feedBO.setFeedState(FeedStateEnum.PUBLISH.getCode());
        feedBO.setFeedAddress(feedVO.getAddress());
        feedBO.setUserId(userId);
        feedBO.setPicUrl(feedVO.getPicUrl());
        feedBO.setVideoUrl(feedVO.getVideoUrl());
        feedBO.setFeedContent(feedVO.getContent());
        feedBO.setLocation(feedVO.getLocation());

        return feedBO;

    }

}
