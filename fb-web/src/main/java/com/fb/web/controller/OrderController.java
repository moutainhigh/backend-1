package com.fb.web.controller;

import com.fb.common.util.DateUtils;
import com.fb.user.response.UserDTO;
import com.fb.web.entity.EnrollVO;
import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.output.JoinListVO;
import com.fb.web.entity.output.OrderDetailInfoVO;
import com.fb.web.entity.output.UserOrderInfoVO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.service.EnrollFacadeService;
import com.fb.web.service.OrderFacadeService;
import com.fb.web.utils.JsonObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/order", produces = "application/json;charset=UTF-8")
@Api(value = "订单", description = "订单相关接口")
@ResponseBody
@Slf4j
public class OrderController {
    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private ActivityFacadeService activityFacadeService;

    @Autowired
    private EnrollFacadeService enrollFacadeService;


    private static final int ENROLL = 2;
    private static final int ORDER = 1;


    @ApiOperation(value = "订单详情", notes = "")
    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    public JsonObject<OrderDetailInfoVO> queryOrderInfoById(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                            @ApiParam(name = "orderId", value = "订单id") @RequestParam("orderId") long orderId) {
        Long userId = sessionUser.getUid();
        OrderDetailInfoVO orderDetailInfoVO = orderFacadeService.getOrderDetail(orderId);
        if (Objects.isNull(orderDetailInfoVO)) {
            return JsonObject.newErrorJsonObject("订单不存在");
        } if (orderDetailInfoVO.getUserId() != userId) {
            return JsonObject.newErrorJsonObject("您无权查看此订单");
        }
        return JsonObject.newCorrectJsonObject(orderDetailInfoVO);
    }


    @ApiOperation(value = "商家查询订单列表", notes = "")
    @RequestMapping(value = "/biz/orderlist", method = {RequestMethod.GET})
    public JsonObject<List<OrderDetailInfoVO>> queryShopOrderList(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                                  @ApiParam(name = "activityId", value = "活动id") @RequestParam("activityId") long activityId,
                                                                  @ApiParam(name = "pageSize", value = "页数") @RequestParam("pageSize") Integer pageSize,
                                                                  @ApiParam(name = "pageNum", value = "页码") @RequestParam("pageNum") Integer pageNum) {
        Long userId = sessionUser.getUid();
        Optional<ActivityDetailVO> activityDetailVO = activityFacadeService.queryActivityById(activityId, userId);
        /*查询活动的发布人是不是当前登录人*/
        if (activityDetailVO.isPresent() && activityDetailVO.get().getUserVO().getUserId().equals(userId)) {
            List<OrderDetailInfoVO> orderDetailInfoVOS = orderFacadeService.getBizOrderList(activityId, pageNum, pageSize);
            return JsonObject.newCorrectJsonObject(orderDetailInfoVOS);
        }
        return JsonObject.newErrorJsonObject("您无权查看此订单");
    }


    @ApiOperation(value = "用户查询订单列表", notes = "")
    @RequestMapping(value = "/user/orderlist", method = {RequestMethod.GET})
    public JsonObject<JoinListVO> queryUserOrderList(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                     @ApiParam(name = "limit", value = "展示条数") @RequestParam("limit") Integer limit,
                                                     @ApiParam(name = "orderOffsetId", value = "订单偏移id，初始给0，需要回传") @RequestParam("orderOffsetId") Long orderOffsetId,
                                                     @ApiParam(name = "enrollOffsetId", value = "报名偏移id，初始给0，需要回传") @RequestParam("enrollOffsetId") Long enrollOffsetId) {
        Long userId = sessionUser.getUid();
        JoinListVO joinListVO = new JoinListVO();
        List<JoinListVO.UserOrderVO> userOrderDetailInfoVOS = Lists.newArrayListWithExpectedSize(limit);
        List<EnrollVO> enrollVOS = null;
        List<UserOrderInfoVO> userOrderInfoVOS = null;

        long callBackOrderOffsetId = -1;
        long callBackEnrollOffsetId = -1;

        if (orderOffsetId != -1) {
            userOrderInfoVOS = orderFacadeService.getUserOrderList(userId, limit, orderOffsetId);
        } if (enrollOffsetId != -1) {
            enrollVOS = enrollFacadeService.queryEnrollList(userId, limit, enrollOffsetId);
        }

        /*混排*/
        if (!CollectionUtils.isEmpty(userOrderInfoVOS) && !CollectionUtils.isEmpty(enrollVOS)) {
            int enrollIndex = 0;
            int orderIndex = 0;

            for (int i = 0; i < limit; i ++) {
                EnrollVO enrollVO = enrollVOS.get(enrollIndex);
                UserOrderInfoVO userOrderInfoVO = userOrderInfoVOS.get(enrollIndex);

                if (enrollVO.getActivityTime().after(userOrderInfoVO.getActivityTime())) {
                    userOrderDetailInfoVOS.add(convertToOrderInfo(enrollVO));
                    enrollIndex ++;
                    callBackEnrollOffsetId = enrollVO.getId();
                } else {
                    userOrderDetailInfoVOS.add(convertToOrderInfo(userOrderInfoVO));
                    orderIndex ++;
                    callBackOrderOffsetId = userOrderInfoVO.getId();
                }
            }
        }
            /*没有报名商家活动*/
            if (CollectionUtils.isEmpty(userOrderInfoVOS) && !CollectionUtils.isEmpty(enrollVOS)) {
                userOrderDetailInfoVOS = enrollVOS.stream().map(enrollVO -> convertToOrderInfo(enrollVO)).limit(limit).collect(Collectors.toList());
                callBackEnrollOffsetId = userOrderDetailInfoVOS.get(userOrderDetailInfoVOS.size() - 1).getActivityId();
            }
            /*没有报名普通活动*/
            if (CollectionUtils.isEmpty(enrollVOS) && !CollectionUtils.isEmpty(userOrderInfoVOS)) {
                userOrderDetailInfoVOS = userOrderInfoVOS.stream().map(userOrderInfoVO -> convertToOrderInfo(userOrderInfoVO)).limit(limit).collect(Collectors.toList());
                callBackOrderOffsetId = userOrderDetailInfoVOS.get(userOrderDetailInfoVOS.size() - 1).getActivityId();
            }


        joinListVO.setOrderInfoVOList(userOrderDetailInfoVOS);
        joinListVO.setOrderOffsetId(callBackOrderOffsetId);
        joinListVO.setEnrollOffsetId(callBackEnrollOffsetId);
        return JsonObject.newCorrectJsonObject(joinListVO);
    }

    private JoinListVO.UserOrderVO convertToOrderInfo(EnrollVO enrollVO) {
        JoinListVO.UserOrderVO userOrderVO = new JoinListVO.UserOrderVO();
        userOrderVO.setActivityId(enrollVO.getActivityId());
        userOrderVO.setActivityName(enrollVO.getActivityName());
        userOrderVO.setActivityTime(DateUtils.getDateFromLocalDateTime(enrollVO.getActivityTime(), DateUtils.dateTimeFormatterMin));
        userOrderVO.setAddress(enrollVO.getAddress());
        userOrderVO.setJoinType(ENROLL);
        return userOrderVO;

    }

    private JoinListVO.UserOrderVO convertToOrderInfo(UserOrderInfoVO userOrderInfoVO) {
        JoinListVO.UserOrderVO userOrderVO = new JoinListVO.UserOrderVO();
        userOrderVO.setActivityId(userOrderInfoVO.getActivityId());
        userOrderVO.setActivityName(userOrderInfoVO.getActivityName());
        userOrderVO.setActivityTime(DateUtils.getDateFromLocalDateTime(userOrderInfoVO.getActivityTime(), DateUtils.dateTimeFormatterMin));
        userOrderVO.setAddress(userOrderInfoVO.getAddress());
        userOrderVO.setMoney(userOrderInfoVO.getMoney());
        userOrderVO.setStatus(userOrderInfoVO.getStatus());
        userOrderVO.setJoinType(ORDER);
        return userOrderVO;

    }

}
