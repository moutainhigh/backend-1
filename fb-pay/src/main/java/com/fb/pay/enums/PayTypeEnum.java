package com.fb.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayTypeEnum {
    ALIPAY(0, "阿里"),
    WEIPAI(1, "微信");

    private int code;

    private String value;
}
