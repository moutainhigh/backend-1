package com.fb.order.service;

import com.fb.order.dto.OrderInfoBO;
import com.fb.order.dto.OrderProductParamBO;
import com.fb.order.enums.OrderStateEnum;

import java.util.List;

public interface OrderService {

    /**
     * 生成订单返回订单号
     *
     * @param orderProductParamBO
     * @return
     */
    String generateOrder(OrderProductParamBO orderProductParamBO);

    /**
     * 根据订单号获取订单信息
     * @param orderId
     * @return
     */
    OrderInfoBO queryOrderUserByOrderId(long orderId);

    /**
     * 根据订单号获取订单信息
     * @param outTradeNo
     * @return
     */
    OrderInfoBO queryOrderUserByOutTradeNo(String outTradeNo);

    /**
     * 翻转订单状态
     * @param outTradeNo
     * @param orderStateEnum
     * @return
     */
    boolean updateOrderState(String outTradeNo, OrderStateEnum orderStateEnum);

    /**
     * 查询订单列表
     * @param userId
     * @param limit
     * @param offsetId
     * @return
     */
    List<OrderInfoBO> queryUserOrderList(Long userId, int limit, long offsetId);

    /**
     * 查询商家订单列表
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OrderInfoBO> queryBizOrderList(Long productId, int pageNum, int pageSize);

}
