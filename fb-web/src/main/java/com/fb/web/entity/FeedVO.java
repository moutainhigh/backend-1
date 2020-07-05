package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "动态模型")
public class FeedVO extends CommonVO {

    @ApiModelProperty(value = "内容", notes = "长度100字")
    private String content;
    @ApiModelProperty(value = "图片url", notes = "多个用,分割（图片6张）")
    private String picUrl;
    @ApiModelProperty(value = "视频url", notes = "多个用,分割（1视频）")
    private String videoUrl;
    @NotNull(message = "clockin is not null")
    @ApiModelProperty(value = "打卡", required = true, allowableValues = "0 否 1 是")
    private Integer clockIn;
    @ApiModelProperty(value = "打卡标签")
    private String clockInTag;
    @NotNull(message = "displayCity is not null")
    @ApiModelProperty(value = "发布到同城", required = true, allowableValues = "0 否 1 是")
    private int displayCity;
    @ApiModelProperty(value = "地址")
    private String address;

}
