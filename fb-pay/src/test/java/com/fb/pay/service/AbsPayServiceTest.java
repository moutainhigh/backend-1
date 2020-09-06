package com.fb.pay.service;
import java.math.BigDecimal;


import com.fb.pay.dto.PayParamBO;
import com.fb.pay.enums.PayTypeEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AbsPayServiceTest extends BaseTest{
    @Autowired
    private AbsPayService absPayService;

    @Test
    public void pay() {
        PayParamBO payParamBO = new PayParamBO();
        payParamBO.setOutTradeNo("test123");
        payParamBO.setUserId(123L);
        payParamBO.setTotalAmount(new BigDecimal("23.12"));


        System.out.println(absPayService.pay(payParamBO, PayTypeEnum.ALIPAY));

    }

    @Test
    public void aliNotify() {
    }
}
