package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "活动模型")
public class LikeVO {

    @NotNull(message = "state is not null")
    @ApiModelProperty(value = "状态 0 取消, 1 点赞", allowableValues = "0 取消, 1 点赞", required = true)
    private Integer state;

    @NotNull(message = "infoId is not null")
    @ApiModelProperty(value = "信息id", required = true)
    private Long infoId;

    @NotNull(message = "infoType is not null")
    @ApiModelProperty(value = "类型 1 活动, 2 动态", allowableValues = "1 活动, 2 动态", required = true)
    private Integer infoType;

}
