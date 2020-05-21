package com.fb.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    SYSTEM_ERROR(-1, "系统"),
    ;

    private int code;

    private String value;
}
