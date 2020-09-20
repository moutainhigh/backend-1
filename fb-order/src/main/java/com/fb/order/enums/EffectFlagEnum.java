package com.fb.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态 0 删除 1 生效
 */
@Getter
@AllArgsConstructor
public enum EffectFlagEnum {
    DELETE(0, "删除"),
    EFFECT(1, "生效"),
   ;

    private int code;

    private String value;
}
