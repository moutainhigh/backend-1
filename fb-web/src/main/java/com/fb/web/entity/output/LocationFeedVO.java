package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
@Api(value = "活动详情")
public class LocationFeedVO {
    @ApiModelProperty(value = "判断是否继续拉取")
    private List<FeedDetailVO> feedDetailVOList;
    @ApiModelProperty(value = "判断是否继续拉取")
    private boolean hasNext;
    @ApiModelProperty(value = "需回传")
    private Long offset;
    @ApiModelProperty(value = "需回传")
    private Integer random;

}
