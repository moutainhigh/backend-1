package com.fb.web.controller;

import com.fb.user.response.UserDTO;
import com.fb.web.entity.LikeVO;
import com.fb.web.service.LikeFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    public JsonObject likeOrCancel(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                   @RequestBody @Validated LikeVO likeParamVO) {
        return JsonObject.newCorrectJsonObject(likeFacadeService.operatorLike(likeParamVO, sessionUser));
    }
}
