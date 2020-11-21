package com.fb.activity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ActivityValidEnum {
    LONG(0, "长期活动"),
    SHORT(1, "短期活动"),
    ;


    private int code;

    private String value;


    public static ActivityValidEnum getActivityTypeEnumByCode(int code) {
        for (ActivityValidEnum activityTypeEnum : ActivityValidEnum.values()) {
            if (activityTypeEnum.getCode() == code) {
                return activityTypeEnum;
            }
        }
        return null;
    }

    public static String getValueByCode(int code) {
        return Objects.nonNull(getActivityTypeEnumByCode(code)) ?
                getActivityTypeEnumByCode(code).getValue() : "";
    }

}
