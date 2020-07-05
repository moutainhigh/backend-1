package com.fb.activity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityStateEnum {
    /*状态 0 删除 1 草稿 2 发布*/

    DELETE(0, "删除"),
    DRAFT(1, "草稿"),
    PUBLISH(2, "发布");

    private int code;

    private String value;

}
