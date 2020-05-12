package com.fb.web.controller;

import com.fb.web.entity.FeedVo;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed")
@Api(value = "动态",description = "动态相关接口")
@Slf4j
public class FeedController {
    @ResponseBody
    @ApiOperation(value = "发布动态", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public JsonObject publishFeed(@RequestBody FeedVo feedVo) throws Exception {
        return JsonObject.newCorrectJsonObject("");

    }
}
