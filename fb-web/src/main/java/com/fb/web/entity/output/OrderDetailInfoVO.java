package com.fb.web.entity.output;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "订单详情")
public class OrderDetailInfoVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private long userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "身份证号")
    private String cardNo;

    @ApiModelProperty(value = "活动时间(直接用就行，不需要再处理)", notes = "yyyy/MM/dd HH:mm")
    private String activityTime;

    @ApiModelProperty(value = "票种id")
    private Long ticketId;

    @ApiModelProperty(value = "票种名称")
    private String ticketName;

    @ApiModelProperty(value = "支付金额")
    private String payMoney;

    @ApiModelProperty(value = "购买时间(直接用就行，不需要再处理)", notes = "yyyy/MM/dd HH:mm")
    private String createTime;
}
