package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "评论入参模型")
public class CommentVO {

    @NotNull(message = "infoId is not null")
    @ApiModelProperty(value = "信息id", required = true)
    private Long infoId;

    @NotNull(message = "infoType is not null")
    @ApiModelProperty(value = "类型 1 活动, 2 动态", allowableValues = "1 活动, 2 动态", required = true)
    private Integer infoType;

    @ApiModelProperty(value = "评论用户")
    private Long userId;

    //如果被评论是null则是直接评论帖子
    @ApiModelProperty(value = "被评论用户", required = true)
    private Long toUserId;

    @NotNull(message = "content is not null")
    @ApiModelProperty(value = "评论内容", required = true)
    private String content;

}
