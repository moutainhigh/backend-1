package com.fb.user.enums;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 12:05
 */
public enum  SexEnum {
    GIRL((byte) 1, "女性"),
    BOY((byte)2, "男性")
    ;

    SexEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    private Byte code;

    private String desc;

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SexEnum getSexEnumByCode(Byte code) {
        for (SexEnum enumConstant : SexEnum.class.getEnumConstants()) {
            if (enumConstant.code.equals(code)) return enumConstant;
        }
        return null;
    }

}
