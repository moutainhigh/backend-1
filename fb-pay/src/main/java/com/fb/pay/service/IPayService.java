package com.fb.pay.service;

import com.fb.pay.dto.PayParamBO;

import java.util.Map;

public interface IPayService {
    /**
     *支付
     * @param payParamBO
     * @return
     */
    String pay(PayParamBO payParamBO);

    /**
     * 支付回调接口
     * @param param
     * @return
     */
    boolean notify(Map<String, String> param);

    boolean refund();
}
