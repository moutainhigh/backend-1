package com.fb.web.service;

import com.fb.feed.dto.FeedBO;
import com.fb.feed.enums.FeedStateEnum;
import com.fb.feed.service.IFeedService;
import com.fb.web.entity.FeedVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FeedFacadeService {

    @Autowired
    private IFeedService feedService;

    public Optional<Long> publishFeed(FeedVO feedVO, Long userId) {
        //TODO LX 地图方法转换
        int cityCode = 111;
        int adCode = 222;
        FeedBO feedBO = voToConvertBO(feedVO, userId);
        feedBO.setCityCode(cityCode);
        feedBO.setAdCode(adCode);
        return feedService.publishFeed(feedBO);
    }

    private FeedBO voToConvertBO(FeedVO feedVO, Long userId) {
        FeedBO feedBO = new FeedBO();
        feedBO.setClockin(feedVO.getClockin());
        feedBO.setClockinTag(feedVO.getClockinTag());
        feedBO.setDisplayCity(feedVO.getDisplayCity());
        feedBO.setFeedState(FeedStateEnum.PUBLISH.getCode());
        feedBO.setFeedAddress(feedVO.getAddress());
        feedBO.setUserId(userId);
        feedBO.setPicUrl(feedVO.getPicUrl());
        feedBO.setVideoUrl(feedVO.getVideoUrl());
        feedBO.setContent(feedVO.getContent());
        return feedBO;

    }

}
