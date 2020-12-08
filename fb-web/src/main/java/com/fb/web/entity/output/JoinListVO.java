package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@Api(value = "我参与的")
public class JoinListVO {
    @ApiModelProperty(value = "我参与的列表")
    private List<UserOrderVO> orderInfoVOList;

    @ApiModelProperty(value = "订单id偏移量")
    private Long orderOffsetId;

    @ApiModelProperty(value = "报名id偏移量")
    private Long enrollOffsetId;

    @Data
    public static class UserOrderVO {

        @ApiModelProperty(value = "id")
        private Long activityId;

        @ApiModelProperty(value = "标题")
        private String activityName;

        @ApiModelProperty(value = "活动时间", notes = "yyyy-MM-dd HH:mm")
        private String activityTime;

        @ApiModelProperty(value = "活动地点")
        private String address;

        @ApiModelProperty(value = "活动费用")
        private String money;

        @ApiModelProperty(value = "订单状态  -2失败 -1关闭 0待支付 1支付成功")
        private Integer status;

        @ApiModelProperty(value = "参与类型  1 订单（报名商家活动走支付） 2 报名（报名普通活动）")
        private Integer joinType;

    }


}
