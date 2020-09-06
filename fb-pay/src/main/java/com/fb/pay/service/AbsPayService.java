package com.fb.pay.service;

import com.alipay.api.internal.util.AlipaySignature;
import com.fb.pay.dto.PayParamBO;
import com.fb.pay.enums.PayTypeEnum;
import com.fb.pay.service.impl.AliPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AbsPayService {

    @Autowired
    private AliPayServiceImpl aliPayServiceImpl;


    public String pay(PayParamBO payParamBO, PayTypeEnum payTypeEnum) {

        return getPayService(payTypeEnum).pay(payParamBO);
    }


    public boolean aliNotify(Map<String, String> param) {

        return aliPayServiceImpl.notify(param);

    }

    private IPayService getPayService(PayTypeEnum payTypeEnum) {
        if (payTypeEnum.equals(PayTypeEnum.ALIPAY)) {
            return aliPayServiceImpl;
        }
        return null;
    }



}
