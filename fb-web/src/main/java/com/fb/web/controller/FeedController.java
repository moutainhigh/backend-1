package com.fb.web.controller;

import com.fb.feed.dto.FeedBO;
import com.fb.feed.service.IFeedService;
import com.fb.web.entity.FeedVO;
import com.fb.web.service.FeedFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/feed")
@Api(value = "动态",description = "动态相关接口")
@Slf4j
public class FeedController {
    @Autowired
    private FeedFacadeService feedFacadeService;

    @ResponseBody
    @ApiOperation(value = "发布动态", notes = "商家和用户")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public JsonObject publishFeed(@RequestBody @Validated FeedVO feedVo) {
        //TODO LX 获取用户uid
        Long userId = 123456L;
        Optional<Long> feedId = feedFacadeService.publishFeed(feedVo, userId);
        if (feedId.isPresent()) {
            return JsonObject.newCorrectJsonObject(feedId.get());
        }
        return JsonObject.newErrorJsonObject("请稍后重试");
    }
}
