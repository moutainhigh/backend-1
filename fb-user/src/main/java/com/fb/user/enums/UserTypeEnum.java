package com.fb.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  UserTypeEnum {

    COMMON_USER((byte) 0, "普通用户"),
    MERCHANT_USER((byte) 1, "入驻用户");

    private Byte code;

    private String desc;

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserTypeEnum getUserTypeEnumByCode(Byte code) {
        for (UserTypeEnum enumConstant : UserTypeEnum.class.getEnumConstants()) {
            if (enumConstant.code.equals(code)) return enumConstant;
        }
        return null;
    }
}
