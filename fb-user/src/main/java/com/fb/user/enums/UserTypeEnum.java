package com.fb.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  UserTypeEnum {

    COMMON_USER((byte) 0, "普通用户"),
    MERCHANT_USER((byte) 1, "入驻用户");

    private byte code;

    private String desc;

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserTypeEnum getUserTypeEnumByCode(byte code) {
        for (UserTypeEnum enumConstant : UserTypeEnum.class.getEnumConstants()) {
            if (enumConstant.code == code) return enumConstant;
        }
        return null;
    }
}
