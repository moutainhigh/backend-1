package com.fb.feed.service;

import com.fb.feed.dto.FeedBO;

import java.util.List;
import java.util.Optional;

public interface IFeedService {

    /**
     * 动态发布
     *
     * @param feedBO
     * @return
     */
    Optional<Long> publishFeed(FeedBO feedBO);

    /**
     * 根据动态id查动态
     * @param feedId
     * @return
     */
    Optional<FeedBO> getFeedById(Long feedId);


    /**
     * 好友动态列表
     * @param userIdList
     * @param limit
     * @param offsetId
     * @return
     */
    Optional<List<FeedBO>> queryFeedListByUid(List<Long> userIdList, int limit, Long offsetId);


    Optional<List<FeedBO>> queryLocationFeedList(String cityCode, int limit, Long offsetId, Integer random);

    /**
     * 查询用户动态列表
     * @param userId
     * @param pageSize
     * @param pageNum
     * @return
     */
    Optional<List<FeedBO>> queryFeedListByUserId(Long userId, int pageSize, int pageNum);


    /**
     * 删除动态
     * @param userId
     * @param feedId
     * @return
     */
    boolean deleteFeed(Long feedId, Long userId);


}
