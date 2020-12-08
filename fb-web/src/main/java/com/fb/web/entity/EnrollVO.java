package com.fb.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EnrollVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "activityId")
    private Long activityId;

    @ApiModelProperty(value = "标题")
    private String activityName;

    @ApiModelProperty(value = "活动时间")
    private Date activityTime;

    @ApiModelProperty(value = "活动地点")
    private String address;
}
