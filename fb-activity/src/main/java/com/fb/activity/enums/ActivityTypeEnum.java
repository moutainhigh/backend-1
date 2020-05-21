package com.fb.activity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityTypeEnum {
    ZHOU_BIAN_YOU(1, "周边游", 1),
    HONG_PA_ZHUO_YOU(2, "轰趴桌游", 0),
    DIAN_YING_YAN_CHU(3, "电影演出", 0),
    HU_WAI_HUO_DONG(4, "户外活动", 0),
    YOU_LE_YUAN(5, "游乐园", 0),
    CHEN_JIN_SHI_YU_LE(6, "沉浸式娱乐", 0),
    WEN_HUA_TI_YAN(7, "文化体验", 0),
    XIN_QI_TAN_SUO(8, "新奇探索", 0),
    ;


    private int code;

    private String value;

    private int activityValid;


}
