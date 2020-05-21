package com.fb.common.exception;


import com.fb.common.enums.ErrorCodeEnum;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6675200699562242562L;

    private ErrorCodeEnum code;

    private String message;


    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(ErrorCodeEnum code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
