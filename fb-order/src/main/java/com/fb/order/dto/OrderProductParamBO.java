package com.fb.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderProductParamBO {

    /*票种id*/
    private Long ticketId;

    /*票种名称*/
    private String ticketName;

    /*产品名称*/
    private String productName;

    /*产品id*/
    private Long productId;

    /*数量*/
    private Integer quantity;

    /*支付金额*/
    private BigDecimal totalAmount;

    /*活动时间*/
    private Date activityTime;

    /**
     * 支付渠道
     * @see PayTypeEnum
     */
    private Integer payType;

    /*用户id*/
    private Long userId;

    /*用户姓名*/
    private String userName;

    /*用户电话*/
    private String userPhone;

    /*身份证号*/
    private String userCardNo;

    /*需要购票信息*/
    private Integer needInfo;


}
