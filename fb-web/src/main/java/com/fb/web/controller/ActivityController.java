package com.fb.web.controller;

import com.fb.activity.service.IActivityService;
import com.fb.web.entity.ActivityVO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activity")
@Api(value = "活动", description = "活动相关接口")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityFacadeService activityFacadeService;


    @ResponseBody
    @ApiOperation(value = "发布活动", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public JsonObject publishActivity(@RequestBody ActivityVO activityVo) {
        //TODO LX
        Long userId = 123456L;
        Optional<Long> activityId = activityFacadeService.publishActivity(activityVo, userId);
        if (activityId.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityId.get());
        }
        return JsonObject.newCorrectJsonObject("");

    }

    @ResponseBody
    @ApiOperation(value = "查询草稿", notes = "商家和用户")
    @RequestMapping(value = "/querydraft", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<ActivityVO> getActivityDraft() {
        //TODO LX
        Long userId = 123456L;
        Optional<ActivityVO> activityVO = activityFacadeService.queryDraft(userId);
        if (activityVO.isPresent()) {
            return JsonObject.newCorrectJsonObject(activityVO.get());
        }
        return JsonObject.newCorrectJsonObject("");
    }
}
