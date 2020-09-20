package com.fb.web.controller;

import com.fb.pay.dto.PayParamBO;
import com.fb.pay.enums.PayTypeEnum;
import com.fb.pay.service.AbsPayService;
import com.fb.web.entity.PayRequestVO;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/pay", produces = "application/json;charset=UTF-8")
@Api(value = "支付", description = "支付相关接口")
@ResponseBody
@Slf4j
public class PayController {

    @Autowired
    private AbsPayService absPayService;


    @ApiOperation(value = "支付", notes = "用户支付接口")
    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public JsonObject getPayInfo(@RequestBody @Validated PayRequestVO payRequestVO) {
        Long userId = 123456L;
        /*生成订单并支付 FIXME*/

        PayParamBO payParamBO = new PayParamBO();
        return JsonObject.newCorrectJsonObject(absPayService.pay(payParamBO, PayTypeEnum.ALIPAY));
    }


    /**
     * 支付宝回调接口
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */

    @RequestMapping(value = "/notify", method = {RequestMethod.POST})
    public String getNotifyInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setHeader("Access-Control-Allow-Origin", "*");

        // 解决POST请求中文乱码问题（推荐使用此种方式解决中文乱码，因为是支付宝发送异步通知使用的是POST请求）
        request.setCharacterEncoding("UTF-8");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 官方demo中使用如下方式解决中文乱码，在此本人不推荐使用，可能会出现中文乱码解决无效的问题。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }

        boolean signVerified = absPayService.aliNotify(params);
        log.info("absPayService.aliNotify signVerified={}", signVerified);
            //调用SDK验证签名
            if (signVerified) {

                //TODO LX 调用订单状态翻转
                // 成功要返回success，不然支付宝会不断发送通知。

                return "success";
            }
        return "fail";
        // 失败要返回fail，不然支付宝会不断发送通知。

    }
}
