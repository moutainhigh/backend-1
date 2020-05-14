package com.fb.web.controller;

import com.fb.web.entity.ActivtyVo;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activty")
@Api(value = "活动", description = "活动相关接口")
@Slf4j
public class ActivtyController {


    @ResponseBody
    @ApiOperation(value = "发布活动", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public JsonObject publishActivty(@RequestBody ActivtyVo ActivtyVo) throws Exception {
        return JsonObject.newCorrectJsonObject("");

    }
    @ResponseBody
    @ApiOperation(value = "查询草稿", notes = "商家和用户")
    @RequestMapping(value = "/querydraft", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<ActivtyVo> getActivtyDraft() throws Exception {
        return JsonObject.newCorrectJsonObject("");
    }
}
