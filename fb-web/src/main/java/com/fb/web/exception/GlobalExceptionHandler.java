package com.fb.web.exception;

import com.fb.common.exception.BusinessException;
import com.fb.web.utils.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonObject handleBusinessException(BusinessException e){
        log.error("BusinessException", e);
        return JsonObject.newErrorJsonObject(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonObject handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return JsonObject.newErrorJsonObject(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JsonObject handleException(Exception e){
        log.error("Exception", e);
        return JsonObject.newErrorJsonObject("哇哦~这个异常我不知道，请联系后端小伙伴解决");
    }
}
