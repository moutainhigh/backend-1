package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "支付")
public class PayRequestVO {

    @NotNull(message = "activityId is not null")
    @ApiModelProperty(value = "活动id", required = true)
    private Long activityId;

    @NotNull(message = "ticketId is not null")
    @ApiModelProperty(value = "票种id", required = true)
    private Long ticketId;

    @ApiModelProperty(value = "活动时间 yyyy-MM-dd HH:mm")
    private String activityTime;

    @NotNull(message = "payType is not null")
    @ApiModelProperty(value = "类型 0 支付宝, 1 微信支付", allowableValues = "0 alipay, 1 weipay", required = true)
    private Integer payType;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    @ApiModelProperty(value = "用户身份证")
    private String userCardNo;

}
