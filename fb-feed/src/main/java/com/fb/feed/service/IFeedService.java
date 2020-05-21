package com.fb.feed.service;

import com.fb.feed.dto.FeedBO;

import java.util.Optional;

public interface IFeedService {

    /**
     * 动态发布
     * @param feedBO
     * @return
     */
    public Optional<Long> publishFeed(FeedBO feedBO);
}
