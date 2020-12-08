package com.fb.web.entity.output;

import com.fb.web.entity.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 */
@Data
@Api(value = "擦肩列表模型")
public class ActivityListVO {

    @ApiModelProperty(value = "参加人数")
    private Long joinCount;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String activityTitle;

    @ApiModelProperty(value = "图片url", notes = "多个用,分割（图片9张）")
    private String picUrl;

    @ApiModelProperty(value = "单人价格")
    private BigDecimal price;

    @ApiModelProperty(value = "拼团价格")
    private BigDecimal assemblePrice;

    @ApiModelProperty(value = "拼团人数")
    private Integer assembleMemberCount;

    @ApiModelProperty(value = "用户信息")
    private UserVO userVO;

    @ApiModelProperty(value = "发布时间，格式yyyy-MM-dd HH:mm")
    private String publishTime;

    @ApiModelProperty(value = "发布地址")
    private String cityName;

    @ApiModelProperty(value = "活动时间，格式yyyy-MM-dd HH:mm")
    private String activityTime;

    @ApiModelProperty(value = "活动人数")
    private Integer memberCount;

    @ApiModelProperty(value = "活动地址")
    private String activityAddress;

    @ApiModelProperty(value = "发布人类型")
    private Integer userType;


}
