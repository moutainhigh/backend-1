package com.fb.order.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fb.order.enums.OrderStateEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInfoBO {
    private Long id;

    /*用户id*/
    private Long userId;

    /*订单编码*/
    private String outTradeNo;

    /*票种id*/
    private Long ticketId;

    /*票种id*/
    private String ticketName;

    /*产品id*/
    private Long productId;

    /*产品id*/
    private String productName;

    /*数量*/
    private Integer quantity;

    /*支付金额*/
    private BigDecimal totalAmount;

    /**
     * 订单状态
     * @see OrderStateEnum
     */
    private Integer orderState;

    /**
     * 支付渠道
     *
     *   @see  com.fb.pay.enums.PayTypeEnum
     */
    private Integer payType;

    /**
     * 逻辑删除标识
     */
    private Integer effectFlag;

    /*活动时间*/
    private Date activityTime;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

    /*订单用户信息*/
    private OrderUserInfoBO orderUserInfoBO;





}
