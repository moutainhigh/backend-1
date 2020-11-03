package com.fb.web.service;
import java.util.*;
import java.math.BigDecimal;

import com.alipay.api.AlipayApiException;
import com.fb.activity.dto.ActivityBO;
import com.fb.activity.service.IActivityService;
import com.fb.common.util.DateUtils;
import com.fb.order.dto.OrderInfoBO;
import com.fb.order.dto.OrderProductParamBO;
import com.fb.order.dto.OrderUserInfoBO;
import com.fb.order.enums.OrderStateEnum;
import com.fb.order.service.OrderService;
import com.fb.pay.dto.PayParamBO;
import com.fb.pay.enums.PayTypeEnum;
import com.fb.pay.service.AbsPayService;
import com.fb.web.entity.PayRequestVO;
import com.fb.web.entity.output.OrderDetailInfoVO;
import com.fb.web.entity.output.UserOrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderFacadeService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AbsPayService absPayService;

    @Autowired
    private IActivityService activityService;

    /*成功*/
    private static final String SUCCESS = "success";
    /*失败*/
    private static final String FAIL = "fail";

    /**
     * 支付预定
     */
    public String orderBooking(PayRequestVO payRequestVO, long userId) {
        String signCode = "";

        /*1查询产品信息填充价格，并返回订单号*/
        Optional<ActivityBO> activityBO = activityService.queryActivityById(payRequestVO.getActivityId(), payRequestVO.getTicketId());

        if (activityBO.isPresent()) {

            OrderProductParamBO orderProductParamBO = new OrderProductParamBO();
            orderProductParamBO.setTicketId(payRequestVO.getTicketId());
            orderProductParamBO.setTicketName(activityBO.get().getTicketBOList().get(0).getTicketName());
            orderProductParamBO.setProductName(activityBO.get().getActivityTitle());
            orderProductParamBO.setProductId(payRequestVO.getActivityId());
            orderProductParamBO.setQuantity(1);
            orderProductParamBO.setTotalAmount(activityBO.get().getTicketBOList().get(0).getTicketPrice());
            orderProductParamBO.setActivityTime(new Date(payRequestVO.getActivityTime()));
            orderProductParamBO.setPayType(payRequestVO.getPayType());
            orderProductParamBO.setUserId(userId);
            orderProductParamBO.setNeedInfo(activityBO.get().getNeedInfo());
            orderProductParamBO.setUserName(payRequestVO.getUserName());
            orderProductParamBO.setUserPhone(payRequestVO.getUserPhone());
            orderProductParamBO.setUserCardNo(payRequestVO.getUserCardNo());

            String outTradeNo = orderService.generateOrder(orderProductParamBO);
            /*2调用支付宝支付产生支付的串码*/
            PayParamBO payParamBO = new PayParamBO();
            payParamBO.setOutTradeNo(outTradeNo);
            payParamBO.setUserId(userId);
            payParamBO.setTotalAmount(new BigDecimal("0"));
            payParamBO.setSubject(orderProductParamBO.getProductName() + "(" + orderProductParamBO.getTicketName() + ")");
            payParamBO.setBody("");

            if (StringUtils.isEmpty(signCode)) {
                signCode = absPayService.pay(payParamBO, PayTypeEnum.ALIPAY);
            }
        }
        return signCode;
    }

    /**
     * 支付回调
     */
    public String notify(Map<String,String> params){
        boolean signVerified = absPayService.aliNotify(params);
        log.info("absPayService.aliNotify params={} , signVerified={}", params, signVerified);
        //调用SDK验证签名
        String outTradeNo = params.get("out_trade_no");
        OrderDetailInfoVO order = getOrderDetail(Long.valueOf(outTradeNo));
        if (checkParam(params, order) && signVerified) {
            //调用订单状态翻转
           boolean flag = orderService.updateOrderState(outTradeNo, OrderStateEnum.SUCCESS);
            // 成功要返回success，不然支付宝会不断发送通知。
            if (flag) {
                return SUCCESS;
            }
        }
        return FAIL;
        // 失败要返回fail，不然支付宝会不断发送通知。
    }
    private boolean checkParam(Map<String,String> params, OrderDetailInfoVO order) {
        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        if (Objects.isNull(order)) {
            log.error("notify out_trade_no错误 params={}", params);
            return false;
        }
        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (String.valueOf(total_amount ).equals(order.getPayMoney())) {
            log.error("notify total_amount params={}", params);
            return false;
        }
        return true;
    }



    /**
     * 用户订单列表
     * @return
     */
    public List<UserOrderInfoVO> getUserOrderList(long userId, int pageNum,int pageSize) {
        List<OrderInfoBO> orderInfoBOS = orderService.queryUserOrderList(userId, pageNum, pageSize);
        if (CollectionUtils.isEmpty(orderInfoBOS)) {
            return null;
        }
       return orderInfoBOS.stream().map(orderInfoBO -> convertToUserVO(orderInfoBO)).collect(Collectors.toList());
    }


    /**
     * 商家订单列表
     * @return
     */
    public List<OrderDetailInfoVO> getBizOrderList(long activityId, int pageNum,int pageSize) {
        List<OrderInfoBO> orderInfoBOS = orderService.queryBizOrderList(activityId, pageNum, pageSize);
        if (CollectionUtils.isEmpty(orderInfoBOS)) {
            return null;
        }
        return orderInfoBOS.stream().map(orderInfoBO -> convertBOToVO(orderInfoBO)).collect(Collectors.toList());
    }


    /*订单详情*/
    public OrderDetailInfoVO getOrderDetail(long orderId) {
        OrderInfoBO orderUserInfoBO = orderService.queryOrderUserByOrderId(orderId);
        return convertBOToVO(orderUserInfoBO);
    }

    private OrderDetailInfoVO convertBOToVO(OrderInfoBO orderInfoBO) {
        if (Objects.isNull(orderInfoBO)) {
            return null;
        }
        OrderDetailInfoVO orderDetailInfoVO = new OrderDetailInfoVO();
        orderDetailInfoVO.setId(orderInfoBO.getId());
        orderDetailInfoVO.setUserId(orderInfoBO.getUserId());
        OrderUserInfoBO orderUserInfoBO = orderInfoBO.getOrderUserInfoBO();
        orderDetailInfoVO.setUserName(orderUserInfoBO.getUserName());
        orderDetailInfoVO.setPhoneNum(orderUserInfoBO.getUserPhone());
        orderDetailInfoVO.setCardNo(orderUserInfoBO.getUserCardNo());
        orderDetailInfoVO.setActivityTime(DateUtils.getDateFromLocalDateTime(orderInfoBO.getActivityTime(), DateUtils.dateTimeFormatterMin));
        orderDetailInfoVO.setTicketId(orderInfoBO.getTicketId());
        orderDetailInfoVO.setTicketName(orderInfoBO.getTicketName());
        orderDetailInfoVO.setPayMoney(orderInfoBO.getTotalAmount().toPlainString());
        orderDetailInfoVO.setCreateTime(DateUtils.getDateFromLocalDateTime(orderInfoBO.getCreateTime(), DateUtils.dateTimeFormatterMin));
        return orderDetailInfoVO;
    }

    private UserOrderInfoVO convertToUserVO(OrderInfoBO orderInfoBO) {
        if (Objects.isNull(orderInfoBO)) {
            return null;
        }
        UserOrderInfoVO userOrderInfoVO = new UserOrderInfoVO();
        userOrderInfoVO.setActivityName(orderInfoBO.getProductName());
        userOrderInfoVO.setActivityTime(String.valueOf(orderInfoBO.getActivityTime()));
        //FIXME
//        userOrderInfoVO.setAddress(orderInfoBO.get);
        userOrderInfoVO.setMoney(orderInfoBO.getTotalAmount().toPlainString());
        return userOrderInfoVO;

    }

}
