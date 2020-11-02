package com.fb.activity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStateEnum {
    DELETE(0, "删除"),
    PUBLISH(1, "发布");

    private int code;

    private String value;

    public static boolean isEffect(int code) {
        if (code == TicketStateEnum.PUBLISH.getCode()) {
            return true;
        }
        return false;
    }

}
