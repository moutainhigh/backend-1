package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@Api(value = "用户购买订单信息")
public class UserOrderInfoVO {
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

    @ApiModelProperty(value = "活动费用")
    private String money;

    @ApiModelProperty(value = "订单状态  -2失败 -1关闭 0待支付 1支付成功")
    private Integer status;


}
