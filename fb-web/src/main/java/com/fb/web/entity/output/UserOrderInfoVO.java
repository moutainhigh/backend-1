package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "用户购买订单信息")
public class UserOrderInfoVO {

    @ApiModelProperty(value = "标题")
    private String activityName;

    @ApiModelProperty(value = "活动时间", notes = "yyyy-MM-dd")
    private String activityTime;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "活动费用")
    private String money;

}
