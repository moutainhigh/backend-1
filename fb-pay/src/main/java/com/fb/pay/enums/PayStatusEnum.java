package com.fb.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {
    WAIT_BUYER_PAY("WAIT_BUYER_PAY"),
    TRADE_CLOSED("TRADE_CLOSED"),
    TRADE_SUCCESS("TRADE_SUCCESS"),
    TRADE_FINISHED("TRADE_FINISHED");

    private String value;

    public static boolean check(String tradeStatus) {
        for(PayStatusEnum payStatusEnum: PayStatusEnum.values()) {
            if (payStatusEnum.getValue().equals(tradeStatus)) {
                return true;
            }
        }
        return false;
    }

}
