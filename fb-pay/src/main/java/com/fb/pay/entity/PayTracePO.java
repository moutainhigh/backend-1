package com.fb.pay.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@TableName("tb_pay_trace")
public class PayTracePO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private String appId;

    @TableField("trade_no")
    private String tradeNo; // 支付宝交易凭证号

    @TableField("out_trade_no")
    private String outTradeNo; // 原支付请求的商户订单号

    @TableField("out_biz_no")
    private String outBizNo; // 商户业务ID，主要是退款通知中返回退款申请的流水号

    @TableField("buyer_id")
    private String buyerId; // 买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字

    @TableField("buyer_logon_id")
    private String buyerLogonId; // 买家支付宝账号

//    @JsonProperty("seller_id")
    @TableField("seller_id")
    private String sellerId; // 卖家支付宝用户号

    @TableField("seller_email")
    private String sellerEmail; // 卖家支付宝账号

    @TableField("trade_status")
    private String tradeStatus; // 交易目前所处的状态，见交易状态说明

    @TableField("total_amount")
    private BigDecimal totalAmount; // 本次交易支付的订单金额

    @TableField("receipt_amount")
    private BigDecimal receiptAmount; // 商家在交易中实际收到的款项

    @TableField("buyer_pay_amount")
    private BigDecimal buyerPayAmount; // 用户在交易中支付的金额

    @TableField("refund_fee")
    private BigDecimal refundFee; // 退款通知中，返回总退款金额，单位为元，支持两位小数

    @TableField("subject")
    private String subject; // 商品的标题/交易标题/订单标题/订单关键字等

    @TableField("body")
    private String body; // 该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来

    @TableField("gmt_create")
    private Date gmtCreate; // 该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss

    @TableField("gmt_payment")
    private Date gmtPayment; // 该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss

    @TableField("gmt_refund")
    private Date gmtRefund; // 该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss

    @TableField("gmt_close")
    private Date gmtClose; // 该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss

    @TableField("fund_bill_list")
    private String fundBillList; // 支付成功的各个渠道金额信息,array

    @TableField("passback_params")
    private String passbackParams; // 公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
