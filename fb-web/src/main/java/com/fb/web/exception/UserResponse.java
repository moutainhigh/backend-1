package com.fb.web.exception;

/**
 * @author: pangminpeng
 * @create: 2020-07-05 17:08
 */
public enum  UserResponse implements BaseResponse{

    SEND_VERIFYCODE_ERROR(10000, "发送验证码失败"),
    USER_NOT_EXIST(10001, "手机号不存在，请注册"),
    VERIFY_FAIL(10002, "手机验证码验证失败"),
    TARGET_USER_NOT_EXIST(10003, "目标用户不存在"),
    USER_TYPE_NOT_VALID(10004, "用户类型非法")
    ;

    private int code;
    private String desc;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    UserResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
