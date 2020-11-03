package com.fb.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.fb.pay.enums.PayTypeEnum;
import com.fb.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@Slf4j
public class PayBeanConfig {
    @Value("${alipay.appId}")
    private String alipay_appId;

    @Value("${alipay.privateKey}")
    private String alipay_privateKey;

//    @Value("${alipay.publicKey}")
//    private String ;

    @Value("${alipay.signType}")
    private String alipay_signType;

    @Value("${alipay.charset}")
    private String alipay_charset;

    @Value("${alipay.gatewayUrl}")
    private String alipay_gatewayUrl;

    @Value("${alipay.format}")
    private String alipay_format;

    @Value("${app.cert.path}")
    public String app_cert_path;

    @Value("${alipay.cert.path}")
    public String alipay_cert_path;

    @Value("${alipay.root.cert.path}")
    public String alipay_root_cert_path;


    @Bean("alipayClient")
    public AlipayClient initAlipayClient() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
//设置网关地址
        certAlipayRequest.setServerUrl(alipay_gatewayUrl);
//设置应用Id
        certAlipayRequest.setAppId(alipay_appId);
//设置应用私钥
        certAlipayRequest.setPrivateKey(alipay_privateKey);
//设置请求格式，固定值json
        certAlipayRequest.setFormat(alipay_format);
//设置字符集
        certAlipayRequest.setCharset(alipay_charset);
//设置签名类型
        certAlipayRequest.setSignType(alipay_signType);
//设置应用公钥证书路径
        certAlipayRequest.setCertPath(getPath(app_cert_path));
//设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(getPath(alipay_cert_path));
//设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(getPath(alipay_root_cert_path));
//构造client
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            log.error("initAlipayClient is error, certAlipayRequest={}",certAlipayRequest, e);
            System.out.println(e);
        }
        return alipayClient;
//        return new DefaultAlipayClient(alipay_gatewayUrl, alipay_appId, alipay_privateKey, alipay_format, alipay_charset, , alipay_signType);
    }



    public  String getPath(String file) {
        File fileCert = new File("/root/t-bear/" + file);

        if(!fileCert.exists()) {
            URL url =  getClass().getClassLoader().getResource(file);
            return url.getPath();
        }
           return "/root/t-bear/" + file;
       }

}
