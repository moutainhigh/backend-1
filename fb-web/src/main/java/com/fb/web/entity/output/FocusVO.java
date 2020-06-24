package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@Api(value = "好友关注列表")
public class FocusVO {
    @ApiModelProperty(value = "列表")
    private List<RelationDetail> relationDetailList;
    @ApiModelProperty(value = "判断是否继续拉取")
    private boolean hasNext;
    @ApiModelProperty(value = "需回传")
    private Long feedOffsetId;
    @ApiModelProperty(value = "需回传")
    private Long activityOffsetId;

}
