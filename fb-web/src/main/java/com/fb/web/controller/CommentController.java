package com.fb.web.controller;

import com.fb.addition.enums.InfoTypeEnum;
import com.fb.user.domain.AbstractUser;
import com.fb.web.entity.CommentVO;
import com.fb.web.entity.output.CommentDetailVO;
import com.fb.web.service.CommentFacadeService;
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

@RestController
@RequestMapping(value = "/comment", produces = "application/json;charset=UTF-8")
@Api(value = "评论", description = "评论相关接口")
@ResponseBody
@Slf4j
public class CommentController {

    @Autowired
    private CommentFacadeService commentFacadeService;

    @ApiOperation(value = "发布评论(二期)", notes = "")
    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    public JsonObject publishComment(@ApiIgnore @RequestAttribute(name = "user") AbstractUser sessionUser,
                                     @RequestBody @Validated CommentVO commentParamVO) {
        Long userId = sessionUser.getUid();
        commentParamVO.setUserId(userId);
        if (commentFacadeService.publishComment(commentParamVO)) {
            return JsonObject.newCorrectJsonObject("");
        }
        return JsonObject.newErrorJsonObject("添加评论失败，请稍后重试！");
    }

    @ApiOperation(value = "分页查询评论(二期)", notes = "")
    @RequestMapping(value = "/query", method = {RequestMethod.GET})
    public JsonObject<List<CommentDetailVO>> getCommentByPage(@ApiParam(name = "infoId") @RequestParam("infoId") long infoId,
//                                                              @ApiParam(name = "infoId") @PathVariable("infoId") Long infoId,
                                                              @ApiParam(name = "infoType", value = "类型 1 活动, 2 动态", allowableValues = "1,2") @RequestParam int infoType,
                                                              @ApiParam(name = "limit", value = "页数") @RequestParam int limit,
                                                              @ApiParam(name = "page", value = "页码") @RequestParam int page) {

        InfoTypeEnum typeEnum = InfoTypeEnum.getInfoTypeEnumByCode(infoType);

        if (Objects.isNull(typeEnum)) {
            return JsonObject.newErrorJsonObject("infoType is illegal !");

        }
        return JsonObject.newCorrectJsonObject(commentFacadeService.getCommentListByPage(infoId, typeEnum, limit, page));

    }
}
