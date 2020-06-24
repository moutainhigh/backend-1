package com.fb.web.controller;

import com.google.common.collect.Lists;


import com.fb.web.entity.output.FeedDetailVO;
import com.fb.web.entity.FeedVO;
import com.fb.web.entity.output.LocationFeedVO;
import com.fb.web.service.FeedFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(value = "/feed", produces = "application/json;charset=UTF-8")
@Api(value = "动态", description = "动态相关接口")
@ResponseBody
@Slf4j
public class FeedController {
    @Autowired
    private FeedFacadeService feedFacadeService;


    @ApiOperation(value = "发布动态", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    public JsonObject publishFeed(@RequestBody @Validated FeedVO feedVo) {
        //    TODO LX 二期
        Long userId = 123456L;
        Optional<Long> feedId = feedFacadeService.publishFeed(feedVo, userId);
        if (feedId.isPresent()) {
            return JsonObject.newCorrectJsonObject(feedId.get());
        }
        return JsonObject.newErrorJsonObject("请稍后重试");
    }

    @ApiOperation(value = "动态详情(二期)", notes = "")
    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    public JsonObject<FeedDetailVO> getFeedInfo(@ApiParam(name = "id", value = "动态id") @RequestParam("id") Long id) {
        Optional<FeedDetailVO> feedDetailVO = feedFacadeService.getFeedDetailById(id);
        if (feedDetailVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(feedDetailVO.get());
        }
        return JsonObject.newCorrectJsonObject("");
    }

    @ApiOperation(value = "动态列表(二期)", notes = "{同城}动态")
    @RequestMapping(value = "/feeds", method = {RequestMethod.GET})
    public JsonObject<LocationFeedVO> getFeedList(@ApiParam(name = "random", value = "随机数") @RequestParam("random") Integer random,
                                                  @ApiParam(name = "limit", value = "条数") @RequestParam("limit") Integer limit,
                                                  @ApiParam(name = "offsetId", value = "偏移量") @RequestParam("offsetId") Long offsetId) {
        /*TODO LX 传入经纬度或者直接是cityCode*/
        String cityCode = "";

        LocationFeedVO locationFeedVO = new LocationFeedVO();

        if (Objects.isNull(offsetId)) {
            offsetId = 0L;
        }
        if (Objects.isNull(random)) {

            random = new Random().nextInt(5);
        }
        if (Objects.isNull(limit) || limit > 20) {
            limit = 10;
        }
        boolean hasNext = false;
        Optional<List<FeedDetailVO>> feedDetailVOList = feedFacadeService.getLocationFeedList(cityCode, limit, offsetId, random);
        locationFeedVO.setFeedDetailVOList(feedDetailVOList.get());
        if (feedDetailVOList.isPresent()) {
            hasNext = limit == feedDetailVOList.get().size() ? true : false;
            offsetId = feedDetailVOList.get().get(feedDetailVOList.get().size() - 1).getId();
        }
        locationFeedVO.setHasNext(hasNext);
        locationFeedVO.setOffset(offsetId);
        locationFeedVO.setRandom(random);

        return JsonObject.newCorrectJsonObject(locationFeedVO);
    }
}
