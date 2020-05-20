package com.fb.feed.service.impl;

import com.fb.feed.dao.FeedDao;
import com.fb.feed.dto.FeedBO;
import com.fb.feed.entity.FeedPO;
import com.fb.feed.service.IFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class FeedServiceImpl implements IFeedService {
    @Autowired
    private FeedDao feedDao;


    @Override
    public Optional<Long> publishFeed(FeedBO feedBO) {
        FeedPO feedPO = bOToConvertPO(feedBO);

        Integer num = feedDao.insert(feedPO);
        if (num > 0) {
            return Optional.of(feedPO.getId());
        }
        return Optional.empty();
    }

    private FeedPO bOToConvertPO(FeedBO feedBO) {
        FeedPO feedPO = new FeedPO();
        feedPO.setUserId(feedBO.getUserId());
        feedPO.setClockin(feedBO.getClockin());
        feedPO.setClockinTag(feedBO.getClockinTag());
        feedPO.setDisplayCity(feedBO.getDisplayCity());
        feedPO.setFeedState(feedBO.getFeedState());
        feedPO.setFeedAddress(feedBO.getFeedAddress());
        feedPO.setCityCode(feedBO.getCityCode());
        feedPO.setAdCode(feedBO.getAdCode());
        feedPO.setPicUrl(feedBO.getPicUrl());
        feedPO.setVideoUrl(feedBO.getVideoUrl());
        feedPO.setFeedContent(feedBO.getContent());
        feedPO.setCreateTime(new Date());
        feedPO.setUpdateTime(new Date());
        return feedPO;

    }
}
