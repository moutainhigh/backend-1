package com.fb.web.controller;

import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.ActivityVO;
import com.fb.web.entity.output.ActivityListVO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/activity", produces = "application/json;charset=UTF-8")
@Api(value = "活动", description = "活动相关接口")
@ResponseBody
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityFacadeService activityFacadeService;


    @ApiOperation(value = "发布活动", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    public JsonObject publishActivity(@RequestBody @Validated ActivityVO activityVo) {
        //TODO LX 获取uid
        //TODO 并校验发布人类型，只有商家才能发布带票种的，普通用户不可以
        Long userId = 123456L;
        Optional<Long> activityId = activityFacadeService.publishActivity(activityVo, userId);
        if (activityId.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityId.get());
        }
        return JsonObject.newCorrectJsonObject("");

    }

    @ApiOperation(value = "查询草稿", notes = "商家和用户")
    @RequestMapping(value = "/querydraft", method = {RequestMethod.GET})
    public JsonObject<ActivityVO> getActivityDraft() {
        //TODO LX 获取uid
        Long userId = 123456L;
        Optional<ActivityVO> activityVO = activityFacadeService.queryDraft(userId);
        if (activityVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityVO.get());
        }
        return JsonObject.newCorrectJsonObject("");
    }


    @ApiOperation(value = "活动详情(二期)", notes = "")
    @RequestMapping(value = "/activity/detail", method = {RequestMethod.GET})
    public JsonObject<ActivityDetailVO> getActivityInfo(@ApiParam(name = "id", value = "活动id") @RequestParam("id") Long id) {
        Optional<ActivityDetailVO> activityVO = activityFacadeService.queryActivityById(id);
        if (activityVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityVO.get());
        }
        return JsonObject.newCorrectJsonObject("");
    }

    //    TODO LX 二期
    @ApiOperation(value = "擦肩(二期)", notes = "传0的时候是全部")
    @RequestMapping(value = "/activities", method = {RequestMethod.GET})
    public JsonObject<List<ActivityListVO>> getActivityList(@ApiParam(name = "activityType", value = "活动类型") @RequestParam("activityType") Integer activityType,
                                                            @ApiParam(name = "pageSize", value = "页数") @RequestParam("pageSize") Integer pageSize,
                                                            @ApiParam(name = "pageNum", value = "页码") @RequestParam("pageNum") Integer pageNum) {
        Optional<List<ActivityListVO>> activityVO = activityFacadeService.queryActivityListByType(activityType, pageSize, pageNum);
        if (activityVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityVO.get());
        }
            return JsonObject.newCorrectJsonObject("");
        }
    }
