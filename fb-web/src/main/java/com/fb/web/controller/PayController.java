package com.fb.web.controller;

import com.fb.user.response.UserDTO;
import com.fb.web.entity.PayRequestVO;
import com.fb.web.service.OrderFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    private OrderFacadeService orderFacadeService;


    /**
     * 客户端吊起支付串码生成
     * @param payRequestVO
     * @return
     */
    @ApiOperation(value = "支付", notes = "用户支付接口")
    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public JsonObject getPayInfo(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                 @RequestBody @Validated PayRequestVO payRequestVO) {

        Long userId = sessionUser.getUid();
         String signCode = orderFacadeService.orderBooking(payRequestVO, userId);
        if (StringUtils.isNotEmpty(signCode)) {
            return JsonObject.newCorrectJsonObject(signCode);
        }
        return JsonObject.newErrorJsonObject("网络不好请重试！");
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

        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
       String result =  orderFacadeService.notify(params);
        log.info("getNotifyInfo req={}, res={}", params, result);
        return result;

    }
}
