package com.fb.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_user")
public class OrderUserPO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*订单表主id*/
    @TableField("order_id")
    private Long orderId;

    /*订单编码*/
    @TableField("out_trade_no")
    private String outTradeNo;

    /*用户名称*/
    @TableField("user_name")
    private String userName;

    /*用户电话*/
    @TableField("user_phone")
    private String userPhone;

    /*身份证号*/
    @TableField("user_card_no")
    private String userCardNo;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
