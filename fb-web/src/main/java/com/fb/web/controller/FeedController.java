package com.fb.web.controller;

import com.fb.user.response.UserDTO;


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
import springfox.documentation.annotations.ApiIgnore;

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
    public JsonObject publishFeed(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                  @RequestBody @Validated FeedVO feedVo) {
        Long userId = sessionUser.getUid();
        Optional<Long> feedId = feedFacadeService.publishFeed(feedVo, userId);
        if (feedId.isPresent()) {
            return JsonObject.newCorrectJsonObject(feedId.get());
        }
        return JsonObject.newErrorJsonObject("请稍后重试");
    }

    @ApiOperation(value = "动态详情(二期)", notes = "")
    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    public JsonObject<FeedDetailVO> getFeedInfo(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                @ApiParam(name = "id", value = "动态id") @RequestParam("id") Long id) {
        Long userId = sessionUser.getUid();
        Optional<FeedDetailVO> feedDetailVO = feedFacadeService.getFeedDetailById(id, userId);
        if (feedDetailVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(feedDetailVO.get());
        }
        return JsonObject.newCorrectJsonObject("");
    }

    @ApiOperation(value = "动态列表(二期)", notes = "{同城}动态")
    @RequestMapping(value = "/feeds", method = {RequestMethod.GET})
    public JsonObject<LocationFeedVO> getFeedList(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                  @ApiParam(name = "random 随机数，初始给0，后面给出参的random", value = "随机数，初始给0，后面给出参的random") @RequestParam("random") Integer random,
                                                  @ApiParam(name = "limit", value = "条数") @RequestParam("limit") Integer limit,
                                                  @ApiParam(name = "offsetId 偏移量,初始给0，后面给出参的offset", value = "偏移量,初始给0，后面给出参的offset") @RequestParam("offsetId") Long offsetId,
                                                  @ApiParam(name = "location", value = "经纬度") @RequestParam("location") String location) {



        Long userId = sessionUser.getUid();
        LocationFeedVO locationFeedVO = new LocationFeedVO();

        if (Objects.isNull(offsetId)) {
            offsetId = 0L;
        }
        if (random == 0) {
            random = new Random().nextInt(5);
        }
        if (Objects.isNull(limit) || limit > 20) {
            limit = 10;
        }
        boolean hasNext = false;

        Optional<List<FeedDetailVO>> feedDetailVOList = feedFacadeService.getLocationFeedList(location, limit, offsetId, random, userId);
        if (feedDetailVOList.isPresent()) {
            locationFeedVO.setFeedDetailVOList(feedDetailVOList.get());
            hasNext = limit == feedDetailVOList.get().size() ? true : false;
            offsetId = feedDetailVOList.get().get(feedDetailVOList.get().size() - 1).getId();
        }
        locationFeedVO.setHasNext(hasNext);
        locationFeedVO.setOffset(offsetId);
        locationFeedVO.setRandom(random);

        return JsonObject.newCorrectJsonObject(locationFeedVO);
    }

    @ApiOperation(value = "我的-动态列表(三期)")
    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    public JsonObject<FeedDetailVO> getFeedList(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                @ApiParam(name = "pageSize", value = "页数") @RequestParam("pageSize") Integer pageSize,
                                                  @ApiParam(name = "pageNum", value = "页码") @RequestParam("pageNum") Integer pageNum) {
        Long userId = sessionUser.getUid();

        Optional<List<FeedDetailVO>> feedDetailVOList = feedFacadeService.queryFeedListByUserId(userId, pageSize, pageNum);

        return JsonObject.newCorrectJsonObject(feedDetailVOList);
    }

    @ApiOperation(value = "我的-动态删除(三期)")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public JsonObject<Boolean> deleteActivity(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                              @ApiParam(name = "id", value = "动态id") @RequestParam("id") Long id) {

        Long userId = sessionUser.getUid();
        boolean flag = feedFacadeService.deleteFeed(id, userId);
        return JsonObject.newCorrectJsonObject(flag);
    }
}
