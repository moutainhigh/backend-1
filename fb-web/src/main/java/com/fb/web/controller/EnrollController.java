package com.fb.web.controller;

import com.fb.activity.dto.ActivityBO;
import com.fb.activity.enums.ActivityStateEnum;
import com.fb.user.response.UserDTO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.service.EnrollFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping(value = "/enroll", produces = "application/json;charset=UTF-8")
@Api(value = "报名", description = "报名相关接口")
@ResponseBody
@Slf4j
public class EnrollController {

    @Autowired
    private EnrollFacadeService enrollFacadeService;
    @Autowired
    private ActivityFacadeService activityFacadeService;


    @ApiOperation(value = "用户报名普通活动（最新）", notes = "用户报名普通活动（非支付活动）")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    public JsonObject publishFeed(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                  @RequestParam @ApiParam("活动id") Long activityId) {
        Long userId = sessionUser.getUid();
        Optional<ActivityBO> activityBO = activityFacadeService.queryActivityById(activityId);

       if (activityBO.isPresent() && ActivityStateEnum.isEffect(activityBO.get().getActivityState())) {
           return JsonObject.newCorrectJsonObject(enrollFacadeService.enrollActivity(userId, activityId, activityBO.get().getUserId()));
       }
        return JsonObject.newErrorJsonObject("此活动不存在");
    }


}
