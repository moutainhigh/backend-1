package com.fb.user.enums;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 12:05
 */
public enum  SexEnum {
    GIRL((byte) 1, "女性"),
    BOY((byte)2, "男性")
    ;

    SexEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private byte code;

    private String desc;

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SexEnum getSexEnumByCode(byte code) {
        for (SexEnum enumConstant : SexEnum.class.getEnumConstants()) {
            if (enumConstant.code == code) return enumConstant;
        }
        return null;
    }

}
