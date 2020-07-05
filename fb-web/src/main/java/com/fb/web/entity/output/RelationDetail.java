package com.fb.web.entity.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RelationDetail {

    @ApiModelProperty(value = "信息类型", allowableValues = "1 活动 2 动态")
    private Integer infoType;

    @ApiModelProperty(value = "时间")
    private LocalDate time;

    @ApiModelProperty(value = "动态")
    private FeedDetailVO feedDetailVO;

    @ApiModelProperty(value = "活动")
    private ActivityDetailVO activityDetailVO;


}
