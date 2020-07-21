package com.fb.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MsgTypeEnum {
    TEXT(1, "文本"),
    VOICE(2, "语音"),
    LOCATION(3, "地理位置"),
    VIDEO(4, "视频"),
    FILE(5, "文件"),
    ;
    private final int code;

    private final String value;


    public static MsgTypeEnum getMsgTypeEnumByCode(int code) {
        for (MsgTypeEnum infoTypeEnum : MsgTypeEnum.values()) {
            if (infoTypeEnum.getCode() == code) {
                return infoTypeEnum;
            }
        }
        return null;
    }

}
