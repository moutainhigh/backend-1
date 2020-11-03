package com.fb.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fb.order.enums.OrderStateEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_order")
public class OrderPO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*订单编码*/
    @TableField("out_trade_no")
    private String outTradeNo;

    /*票种id*/
    @TableField("ticket_id")
    private Long ticketId;

    /*票种id*/
    @TableField("ticket_name")
    private String ticketName;

    /*产品id*/
    @TableField("product_id")
    private Long productId;

    /*产品id*/
    @TableField("product_name")
    private String productName;

    /*数量*/
    @TableField("quantity")
    private Integer quantity;

    /*支付金额*/
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单状态
     * @see OrderStateEnum
     */
    @TableField("order_state")
    private Integer orderState;

    /**
     * 支付渠道
     * @see PayTypeEnum
     */
    @TableField("pay_type")
    private Integer payType;

    /***
     * 是否需要身份信息
     */
    @TableField("need_info")
    private Integer needInfo;

    /**
     * 逻辑删除标识
     */
    @TableField("effect_flag")
    private Integer effectFlag;

    /*活动时间*/
    @TableField("activity_time")
    private Date activityTime;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
