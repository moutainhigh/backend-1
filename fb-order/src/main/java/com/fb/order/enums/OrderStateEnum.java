package com.fb.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态 -2支付失败 -1关闭 0 待支付 1支付成功
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum {
    FAILURE(-2, "失败"),
    CLOSE(-1, "关闭"),
    WAIT(0, "待支付"),
    SUCCESS(1, "支付成功");

    private int code;

    private String value;
}
