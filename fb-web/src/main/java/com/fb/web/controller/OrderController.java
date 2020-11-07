package com.fb.web.controller;

import com.fb.user.domain.AbstractUser;
import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.output.OrderDetailInfoVO;
import com.fb.web.entity.output.UserOrderInfoVO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.service.OrderFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @ApiOperation(value = "订单详情", notes = "")
    @RequestMapping(value = "/detail", method = {RequestMethod.GET})
    public JsonObject<OrderDetailInfoVO> queryOrderInfoById(@ApiIgnore @RequestAttribute(name = "user") AbstractUser sessionUser,
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
    public JsonObject<List<OrderDetailInfoVO>> queryShopOrderList(@ApiIgnore @RequestAttribute(name = "user") AbstractUser sessionUser,
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
    public JsonObject<UserOrderInfoVO> queryUserOrderList(@ApiIgnore @RequestAttribute(name = "user") AbstractUser sessionUser,
                                                          @ApiParam(name = "pageSize", value = "页数") @RequestParam("pageSize") Integer pageSize,
                                                          @ApiParam(name = "pageNum", value = "页码") @RequestParam("pageNum") Integer pageNum) {
        Long userId = sessionUser.getUid();
        List<UserOrderInfoVO>  userOrderDetailInfoVOS = orderFacadeService.getUserOrderList(userId, pageNum, pageSize);
        return JsonObject.newCorrectJsonObject(userOrderDetailInfoVOS);
    }

}
