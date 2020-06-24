package com.fb.addition.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InfoTypeEnum {
    ACTIVITY(1, "活动"),
    FEED(2, "动态");

    private int code;

    private String value;

    public static InfoTypeEnum getInfoTypeEnumByCode(int code) {
        for (InfoTypeEnum infoTypeEnum : InfoTypeEnum.values()) {
            if (infoTypeEnum.getCode() == code) {
                return infoTypeEnum;
            }
        }
        return null;
    }

}
