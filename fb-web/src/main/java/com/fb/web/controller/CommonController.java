package com.fb.web.controller;

import com.fb.activity.enums.ActivityTypeEnum;
import com.fb.web.utils.JsonObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/common")
@Api(value = "通用", description = "通用接口")
@Slf4j
public class CommonController {

    @ResponseBody
    @ApiOperation(value = "所有活动类型", notes = "字典")
    @RequestMapping(value = "/activitytype", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<Map<Integer, String>> getActivityType() {
        Map<Integer, String> result = Maps.newHashMap();
        for (ActivityTypeEnum activityTypeEnum:ActivityTypeEnum.values()) {
            result.put(activityTypeEnum.getCode(), activityTypeEnum.getValue());
        }
        return JsonObject.newCorrectJsonObject(result);
    }

    //TODO LX
    @ResponseBody
    @ApiOperation(value = "云配置", notes = "上传云配置（需要调研用到哪些配置，申请完之后再给）")
    @RequestMapping(value = "/uploadconfig", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<Object> getUploadYun() {
        return JsonObject.newCorrectJsonObject("");
    }
}
