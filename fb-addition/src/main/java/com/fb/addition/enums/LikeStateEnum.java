package com.fb.addition.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeStateEnum {
    CANCEL(0, "取消"),
    LIKE(1, "点赞"),
    ;

    private int code;

    private String value;

}
