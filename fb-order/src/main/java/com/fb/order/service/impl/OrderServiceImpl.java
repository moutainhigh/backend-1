package com.fb.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.common.util.SnowFlakeUtils;
import com.fb.order.dao.OrderDAO;
import com.fb.order.dao.OrderUserDAO;
import com.fb.order.dto.OrderInfoBO;
import com.fb.order.dto.OrderProductParamBO;
import com.fb.order.dto.OrderUserInfoBO;
import com.fb.order.entity.OrderPO;
import com.fb.order.entity.OrderUserPO;
import com.fb.order.enums.EffectFlagEnum;
import com.fb.order.enums.OrderStateEnum;
import com.fb.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderUserDAO orderUserDAO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String generateOrder(OrderProductParamBO orderProductParamBO) {
        String outTradeNo = String.valueOf(SnowFlakeUtils.snowflakeId());
        OrderPO orderPO = convertBOToPO(orderProductParamBO, outTradeNo);
        orderDAO.insert(orderPO);
        orderUserDAO.insert(convertBOToUserPO(orderProductParamBO, outTradeNo, orderPO.getId()));
        return String.valueOf(outTradeNo);
    }

    /**
     * 根据外部订单号查询订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfoBO queryOrderUserByOrderId(long orderId) {
        QueryWrapper<OrderUserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(OrderUserPO::getOrderId, orderId);
        });

        OrderUserPO orderUserPO = orderUserDAO.selectOne(queryWrapper);
        return convertPOToBO(orderDAO.selectById(orderId),orderUserPO);
    }

    /**
     * 更新订单状态
     *
     * @param outTradeNo
     * @param orderStateEnum
     * @return
     */
    @Override
    public boolean updateOrderState(String outTradeNo, OrderStateEnum orderStateEnum) {
        OrderPO orderPO = getOrderPO(outTradeNo);
//        FIXME 校验
        if (Objects.nonNull(orderPO) /* && orderPO.getUserId().equals(userId)*/) {
            orderPO.setOrderState(orderStateEnum.getCode());
            return orderDAO.updateById(orderPO) > 0 ? true : false;
        }
        log.warn("order is not exist, outTradeNo={}", outTradeNo);
        return false;
    }

    /**
     * 用户查看个人的订单列表，所以不限制状态
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderInfoBO> queryUserOrderList(Long userId, int pageNum, int pageSize) {
        List<OrderInfoBO> orderInfoBOS = new ArrayList<>(pageSize);
        QueryWrapper<OrderPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(OrderPO::getUserId, userId);
        }).orderByDesc(OrderPO::getCreateTime);

        IPage<OrderPO> orderList = orderDAO.selectPage(new Page(pageNum, pageSize), queryWrapper);
        if (!CollectionUtils.isEmpty(orderList.getRecords())) {
            Map<Long, OrderUserPO> orderIdUserPoMap = getOrderIdUserPOMap(orderList.getRecords().stream().map(OrderPO::getId).collect(Collectors.toList()));
            orderInfoBOS = orderList.getRecords().stream().map(orderPO -> convertPOToBO(orderPO, orderIdUserPoMap.get(orderPO.getId()))).collect(Collectors.toList());
        }
        return orderInfoBOS;
    }

    /**
     * 商家查看订单列表，要限制状态
     *
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderInfoBO> queryBizOrderList(Long productId, int pageNum, int pageSize) {
        List<OrderInfoBO> orderInfoBOS = new ArrayList<>(pageSize);
        QueryWrapper<OrderPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(OrderPO::getProductId, productId);
//            obj.eq(OrderPO::getTicketId, ticketId);
            obj.eq(OrderPO::getOrderState, OrderStateEnum.SUCCESS.getCode());

        }).orderByDesc(OrderPO::getCreateTime);

        IPage<OrderPO> orderList = orderDAO.selectPage(new Page(pageNum, pageSize), queryWrapper);
        if (!CollectionUtils.isEmpty(orderList.getRecords())) {
            Map<Long, OrderUserPO> orderIdUserPoMap = getOrderIdUserPOMap(orderList.getRecords().stream().map(OrderPO::getId).collect(Collectors.toList()));
            orderInfoBOS = orderList.getRecords().stream().map(orderPO -> convertPOToBO(orderPO, orderIdUserPoMap.get(orderPO.getId()))).collect(Collectors.toList());
        }
        return orderInfoBOS;
    }

    private OrderPO getOrderPO(String outTradeNo) {
        QueryWrapper<OrderPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(OrderPO::getOutTradeNo, outTradeNo);
        });

        OrderPO orderPO = orderDAO.selectOne(queryWrapper);
        return orderPO;
    }

    private Map<Long, OrderUserPO> getOrderIdUserPOMap(List<Long> orderIdList) {
        QueryWrapper<OrderUserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.in(OrderUserPO::getOrderId, orderIdList);
        });

        List<OrderUserPO> orderUserPOList = orderUserDAO.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(orderUserPOList)) {
            return orderUserPOList.stream().collect(Collectors.toMap(OrderUserPO::getOrderId, Function.identity()));
        }
        return null;
    }

    private OrderInfoBO convertPOToBO(OrderPO orderPO, OrderUserPO userPO) {
        if (Objects.isNull(orderPO)) {
            return null;
        }
        OrderInfoBO orderInfoBO = new OrderInfoBO();
        orderInfoBO.setId(orderPO.getId());
        orderInfoBO.setUserId(orderPO.getUserId());
        orderInfoBO.setOutTradeNo(orderPO.getOutTradeNo());
        orderInfoBO.setTicketId(orderPO.getTicketId());
        orderInfoBO.setTicketName(orderPO.getTicketName());
        orderInfoBO.setProductId(orderPO.getProductId());
        orderInfoBO.setProductName(orderPO.getProductName());
        orderInfoBO.setQuantity(orderPO.getQuantity());
        orderInfoBO.setTotalAmount(orderPO.getTotalAmount());
        orderInfoBO.setOrderState(orderPO.getOrderState());
        orderInfoBO.setPayType(orderPO.getPayType());
        orderInfoBO.setEffectFlag(orderPO.getEffectFlag());
        orderInfoBO.setActivityTime(orderPO.getActivityTime());
        orderInfoBO.setCreateTime(orderPO.getCreateTime());
        orderInfoBO.setUpdateTime(orderPO.getUpdateTime());
        if (Objects.nonNull(userPO)) {
            orderInfoBO.setOrderUserInfoBO(convertPOToUserBO(userPO));
        }
        return orderInfoBO;
    }

    private OrderUserInfoBO convertPOToUserBO(OrderUserPO userPO) {
        OrderUserInfoBO orderUserInfoBO = new OrderUserInfoBO();
        orderUserInfoBO.setId(userPO.getId());
        orderUserInfoBO.setUserName(userPO.getUserName());
        orderUserInfoBO.setUserPhone(userPO.getUserPhone());
        orderUserInfoBO.setUserCardNo(userPO.getUserCardNo());
        return orderUserInfoBO;

    }

    private OrderPO convertBOToPO(OrderProductParamBO orderProductParamBO, String outTradeNo) {
        OrderPO orderPO = new OrderPO();
        orderPO.setUserId(orderProductParamBO.getUserId());
        orderPO.setOutTradeNo(outTradeNo);
        orderPO.setTicketId(orderProductParamBO.getTicketId());
        orderPO.setTicketName(orderProductParamBO.getTicketName());
        orderPO.setProductId(orderProductParamBO.getProductId());
        orderPO.setProductName(orderProductParamBO.getProductName());
        orderPO.setQuantity(orderProductParamBO.getQuantity());
        orderPO.setTotalAmount(orderProductParamBO.getTotalAmount());
        orderPO.setOrderState(OrderStateEnum.WAIT.getCode());
        orderPO.setPayType(orderProductParamBO.getPayType());
        orderPO.setEffectFlag(EffectFlagEnum.EFFECT.getCode());
        orderPO.setActivityTime(orderProductParamBO.getActivityTime());
        return orderPO;
    }

    private OrderUserPO convertBOToUserPO(OrderProductParamBO orderProductParamBO, String outTradeNo, long orderId) {
        OrderUserPO orderUserPO = new OrderUserPO();
        orderUserPO.setOrderId(orderId);
        orderUserPO.setUserId(orderProductParamBO.getUserId());
        orderUserPO.setOutTradeNo(outTradeNo);
        orderUserPO.setUserName(orderProductParamBO.getUserName());
        orderUserPO.setUserPhone(orderProductParamBO.getUserPhone());
        orderUserPO.setUserCardNo(orderProductParamBO.getUserCardNo());
        return orderUserPO;
    }
}
