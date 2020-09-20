package com.fb.web.service;

import com.fb.order.dto.OrderInfoBO;
import com.fb.order.dto.OrderUserInfoBO;
import com.fb.order.service.OrderService;
import com.fb.web.entity.output.OrderDetailInfoVO;
import com.fb.web.entity.output.UserOrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderFacadeService {

    @Autowired
    private OrderService orderService;
    /**
     * 用户订单列表
     * @return
     */
    public List<UserOrderInfoVO> getUserOrderList(long userId, int pageNum,int pageSize) {
        List<OrderInfoBO> orderInfoBOS = orderService.queryUserOrderList(userId, pageNum, pageSize);

    }


    /**
     * 商家订单列表
     * @return
     */
    public List<OrderDetailInfoVO> getBizOrderList(long activityId, int pageNum,int pageSize) {
        List<OrderInfoBO> orderInfoBOS = orderService.queryBizOrderList(activityId, pageNum, pageSize);
        if (orderInfoBOS.isEmpty()) {

        }
        return null;
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
        orderDetailInfoVO.setActivityTime(orderInfoBO.getActivityTime());
        orderDetailInfoVO.setTicketId(orderInfoBO.getTicketId());
        orderDetailInfoVO.setTicketName(orderInfoBO.getTicketName());
        orderDetailInfoVO.setPayMoney(orderInfoBO.getTotalAmount().toPlainString());
        orderDetailInfoVO.setCreateTime(orderInfoBO.getCreateTime());
        return orderDetailInfoVO;


    }

}
