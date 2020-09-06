package com.fb.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fb.common.util.JsonUtils;
import com.fb.pay.dto.PayParamBO;
import com.fb.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AliPayServiceImpl implements IPayService {
    @Value("${alipay.appId}")
    private String alipay_appId;

    @Value("${alipay.privateKey}")
    private String alipay_privateKey;

    // 支付宝公钥（请注意，不是商户公钥）
    @Value("${alipay.publicKey}")
    private String alipay_publicKey;

    @Value("${alipay.signType}")
    private String alipay_signType;

    @Value("${alipay.charset}")
    private String alipay_charset;

    @Value("${alipay.notifyUrl}")
    private String alipay_notifyUrl;

    @Value("${alipay.format}")
    private String alipay_format;

    @Autowired
    private AlipayClient alipayClient;


    @Override
    public String pay(PayParamBO payParamBO) {

        // 注意：不同接口这里的请求对象是不同的，这个可以查看蚂蚁金服开放平台的API文档查看
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody("XXX");
        model.setSubject("活动门票");
        // 唯一订单号 根据项目中实际需要获取相应的 FIXME
        model.setOutTradeNo("test123"/*payParamBO.getOutTradeNo()*/);
        // 支付超时时间（根据项目需要填写）
        model.setTimeoutExpress("30m");
        // 支付金额（项目中实际订单的需要支付的金额，金额的获取与操作请放在服务端完成，相对安全） FIXME
        model.setTotalAmount("1.11"/*payParamBO.getTotalAmount().toPlainString()*/);
        model.setProductCode("QUICK_MSECURITY_PAY");
        alipayRequest.setBizModel(model);
        // 支付成功后支付宝异步通知的接收地址url
        alipayRequest.setNotifyUrl(alipay_notifyUrl);

        // 注意：每个请求的相应对象不同，与请求对象是对应。
        AlipayTradeAppPayResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.sdkExecute(alipayRequest);
            System.out.println("alipayResponse ={}" + JsonUtils.object2Json(alipayResponse));
        } catch (AlipayApiException e) {
            log.error("AliPayServiceImpl pay is error, alipayRequest={}", JsonUtils.object2Json(alipayRequest), e);
        }
        return alipayResponse.getBody();
    }

    @Override
    public boolean notify(Map<String, String> param) {
        //TODO LX
        // 一般需要判断支付状态是否为TRADE_SUCCESS
        // 更严谨一些还可以判断 1.appid 2.sellerId 3.out_trade_no 4.total_amount 等是否正确，正确之后再进行相关业务操作。
        boolean signVerified = false;
            //调用SDK验证签名
            try {
                signVerified = AlipaySignature.rsaCheckV1(param, alipay_publicKey, alipay_charset, alipay_signType);
            } catch (AlipayApiException e) {
                log.error("AliPayServiceImpl notify is error, alipayRequest={}", param, e);
            }
            // 验签失败  笔者在这里是输出log，可以根据需要做一些其他操作
            if (signVerified == false) {
                log.warn("AliPayServiceImpl notify is error, alipayRequest={}", param);
            }
            // 失败要返回fail，不然支付宝会不断发送通知。

        return signVerified;
    }


    @Override
    public boolean refund(/*String orgOrderId, Integer orderId, BigDecimal refundAmount*/) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        Map<String, Object> jsonObject = new HashMap<>();
//        jsonObject.put("out_trade_no", String.valueOf(orderId));
//        jsonObject.put("trade_no", orgOrderId);
//        jsonObject.put("refund_amount", String.format("%.2f", refundAmount.doubleValue()));
        String bizContent = JsonUtils.object2Json(jsonObject);
        request.setBizContent(bizContent);

        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("AliPayServiceImpl refund is error, alipayRequest={}", bizContent, e);
        }
        if (response.isSuccess()) {
            log.info("AliPayServiceImpl refund success，");
            return true;
        }
        log.warn("AliPayServiceImpl refund failure，");

        return false;
    }
}
