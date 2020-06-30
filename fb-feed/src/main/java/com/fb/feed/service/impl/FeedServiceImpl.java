package com.fb.feed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.feed.dao.FeedDAO;
import com.fb.feed.dto.FeedBO;
import com.fb.feed.entity.FeedPO;
import com.fb.feed.enums.FeedStateEnum;
import com.fb.feed.service.IFeedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FeedServiceImpl implements IFeedService {
    private static final int GROUP = 5;

    @Autowired
    private FeedDAO feedDao;


    @Override
    public Optional<Long> publishFeed(FeedBO feedBO) {
        FeedPO feedPO = bOToConvertPO(feedBO);
        Integer num = feedDao.insert(feedPO);
        if (num > 0) {
            return Optional.of(feedPO.getId());
        }
        return Optional.empty();
    }

    @Override
    public Optional<FeedBO> getFeedById(Long feedId) {
        FeedPO feedPO = feedDao.selectById(feedId);
        return Optional.ofNullable(pOToConvertBO(feedPO));
    }

    @Override
    public Optional<List<FeedBO>> queryFeedListByUid(List<Long> userIdList, int limit, Long offsetId) {
        List<FeedBO> feedBOS = new ArrayList<>(limit);
        QueryWrapper<FeedPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            if (Objects.nonNull(offsetId) && offsetId > 0) {
                obj.lt(FeedPO::getId, offsetId);
            }
            if (!CollectionUtils.isEmpty(userIdList)) {
                obj.in(FeedPO::getUserId, userIdList);
            }
            obj.eq(FeedPO::getFeedState, FeedStateEnum.PUBLISH.getCode());
        }).orderByDesc(FeedPO::getUpdateTime);

        IPage<FeedPO> feedList = feedDao.selectPage(new Page(1, limit), queryWrapper);
        if (!CollectionUtils.isEmpty(feedList.getRecords())) {
            feedList.getRecords().forEach(feedPO -> {
                feedBOS.add(pOToConvertBO(feedPO));
            });
        }
        return Optional.ofNullable(feedBOS);
    }

    @Override
    public Optional<List<FeedBO>> queryLocationFeedList(String cityCode, int limit, Long offsetId, Integer random) {

        List<FeedBO> feedBOS = new ArrayList<>(limit);
        QueryWrapper<FeedPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            if (Objects.nonNull(offsetId) && offsetId > 0) {
                obj.lt(FeedPO::getId, offsetId);
            }
            obj.eq(FeedPO::getFeedState, FeedStateEnum.PUBLISH.getCode());
            obj.eq(FeedPO::getDisplayCity, 1);

        }).orderByDesc(FeedPO::getUpdateTime);

        IPage<FeedPO> feedList = feedDao.selectPage(new Page(1, 1000), queryWrapper);

        if (!CollectionUtils.isEmpty(feedList.getRecords())) {
             List<FeedPO> feedPOList = feedList.getRecords().stream().filter(feedPO -> feedPO.getId() % GROUP == random).limit(limit).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(feedPOList)) {
                feedList.getRecords().forEach(feedPO -> {
                    feedBOS.add(pOToConvertBO(feedPO));
                });
            }
        }
        return Optional.ofNullable(feedBOS);
    }


    private FeedBO pOToConvertBO(FeedPO feedPO) {
        FeedBO feedBO = new FeedBO();
        if (Objects.isNull(feedPO)) {
            return null;
        }
        BeanUtils.copyProperties(feedPO, feedBO);
        return feedBO;

    }


    private FeedPO bOToConvertPO(FeedBO feedBO) {
        if (Objects.isNull(feedBO)) {
            return null;
        }
        FeedPO feedPO = new FeedPO();
        BeanUtils.copyProperties(feedBO, feedPO);
        return feedPO;

    }
}
