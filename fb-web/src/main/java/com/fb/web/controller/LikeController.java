package com.fb.web.controller;

import com.fb.web.entity.LikeVO;
import com.fb.web.service.LikeFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/like", produces = "application/json;charset=UTF-8")
@Api(value = "点赞", description = "点赞相关接口")
@ResponseBody
@Slf4j
public class LikeController {

    @Autowired
    private LikeFacadeService likeFacadeService;

    @ApiOperation(value = "点赞(二期)", notes = "")
    @RequestMapping(value = "/operate", method = {RequestMethod.POST})
    public JsonObject likeOrCancel(@RequestBody @Validated LikeVO likeParamVO) {
        //TODO LX 获取点赞用户uid
        Long userId = 123456L;
        return JsonObject.newCorrectJsonObject(likeFacadeService.operatorLike(likeParamVO, userId));
    }
}
