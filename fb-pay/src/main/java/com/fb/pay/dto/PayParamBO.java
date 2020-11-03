package com.fb.pay.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayParamBO {
    /*商户订单*/
    private String outTradeNo;

    /*用户id*/
    private Long userId;

    /*付款金额*/
    private BigDecimal totalAmount;

    /*主题*/
    private String subject;

    /*内容*/
    private String body;



}
