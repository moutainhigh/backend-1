package com.fb.feed.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedStateEnum {
    DELETE(0, "删除"),
    PUBLISH(1, "发布中");

    private int code;

    private String value;
}
