package com.fb.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.fb.pay.enums.PayTypeEnum;
import com.fb.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class PayBeanConfig {
    @Value("${alipay.appId}")
    private String alipay_appId;

    @Value("${alipay.privateKey}")
    private String alipay_privateKey;

    @Value("${alipay.publicKey}")
    private String alipay_publicKey;

    @Value("${alipay.signType}")
    private String alipay_signType;

    @Value("${alipay.charset}")
    private String alipay_charset;

    @Value("${alipay.gatewayUrl}")
    private String alipay_gatewayUrl;

    @Value("${alipay.format}")
    private String alipay_format;


/*
    @Value("${alipay.notifyUrl}")
    private String alipay_notifyUrl;

    @Value("${alipay.returnUrl}")
    private Integer alipay_returnUrl;*/

    @Bean("alipayClient")
    public AlipayClient initAlipayClient() {

        return new DefaultAlipayClient(alipay_gatewayUrl, alipay_appId, alipay_privateKey, alipay_format, alipay_charset, alipay_publicKey, alipay_signType);

    }

}
