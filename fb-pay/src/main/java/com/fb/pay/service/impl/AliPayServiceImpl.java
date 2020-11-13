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
import com.fb.pay.dao.PayTraceDAO;
import com.fb.pay.dto.PayParamBO;
import com.fb.pay.entity.PayTracePO;
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

    /*支付宝支付操作代码*/
    private final static String PAY_CODE = "QUICK_MSECURITY_PAY";
    /*支付超时时间*/
    private final static String PAY_TIMEOUT = "30m";

    @Autowired
    private PayTraceDAO payTraceDAO;


    /*
    支付功能，只是单纯的支付，存储订单等聚合操作在站点层的service中
     */
    @Override
    public String pay(PayParamBO payParamBO) {

        // 注意：不同接口这里的请求对象是不同的，这个可以查看蚂蚁金服开放平台的API文档查看
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payParamBO.getBody());
        model.setSubject(payParamBO.getSubject());
        // 唯一订单号 根据项目中实际需要获取相应的
        model.setOutTradeNo(payParamBO.getOutTradeNo());
        // 支付超时时间（根据项目需要填写）
        model.setTimeoutExpress(PAY_TIMEOUT);
        // 支付金额（项目中实际订单的需要支付的金额，金额的获取与操作请放在服务端完成，相对安全）
        model.setTotalAmount(payParamBO.getTotalAmount().toPlainString());
        model.setProductCode(PAY_CODE);
        alipayRequest.setBizModel(model);
        // 支付成功后支付宝异步通知的接收地址url
        alipayRequest.setNotifyUrl(alipay_notifyUrl);

        AlipayTradeAppPayResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.sdkExecute(alipayRequest);
//            System.out.println("alipayRequest ={}" + JsonUtils.object2Json(alipayRequest));
//            System.out.println("alipayResponse ={}" + JsonUtils.object2Json(alipayResponse));
            log.info("pay alipayRequest={},alipayResponse={}",alipayRequest, alipayResponse);
        } catch (AlipayApiException e) {
            log.error("AliPayServiceImpl pay is error, alipayRequest={}", JsonUtils.object2Json(alipayRequest), e);
        }
        return alipayResponse.getBody();
    }

    @Override
    public boolean notify(Map<String, String> params) {

        boolean signVerified = false;
            //调用SDK验证签名
            try {
                // 更严谨一些还可以判断 1.appid 2.sellerId 3.out_trade_no 4.total_amount 等是否正确，正确之后再进行相关业务操作。
                check(params);
                signVerified = AlipaySignature.rsaCheckV1(params, alipay_publicKey, alipay_charset, alipay_signType);
                // 记录交易流水
                payTraceDAO.insert(buildAlipayNotifyParam(params));
            } catch (AlipayApiException e) {
                log.error("AliPayServiceImpl notify is error, alipayRequest={}", params, e);
            } catch (Exception e) {
                log.error("AliPayServiceImpl notify is error, alipayRequest={}", params, e);
            }
            // 验签失败  笔者在这里是输出log，可以根据需要做一些其他操作
            if (signVerified == false) {
                log.warn("AliPayServiceImpl notify is error, alipayRequest={}", params);
            }
            // 失败要返回fail，不然支付宝会不断发送通知。
        return signVerified;
    }

    private PayTracePO buildAlipayNotifyParam(Map<String, String> params) {
        String json = JsonUtils.object2Json(params);
        return JsonUtils.json2Object(json, PayTracePO.class);
    }
    /**
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     * 一般需要判断支付状态是否为TRADE_SUCCESS
     * @param params
     * @throws AlipayApiException
     */
    private void check(Map<String, String> params) throws AlipayApiException {
        //只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功
        if (params.get("trade_status").equals("TRADE_SUCCESS") || params.get("trade_status").equals("TRADE_FINISHED")) {
            // 验证app_id是否为该商户本身。
            if (!params.get("app_id").equals(alipay_appId)) {
                throw new AlipayApiException("app_id不一致");
            }
        }
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
