package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Api(value = "活动模型")
public class ActivityVO extends CommonVO {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "发布人类型")
    private Integer userType;
    @ApiModelProperty(value = "发布人类型")
    private Long userId;
    @NotNull(message = "activityTitle is not null")
    @ApiModelProperty(value = "活动标题", required = true)
    private String activityTitle;
    @ApiModelProperty(value = "活动人数")
    private Integer memberCount;
    @ApiModelProperty(value = "有效期", allowableValues = "0 长期 1 短期")
    private Integer activityValid;
    @NotNull(message = "activityTime is not null")
    @ApiModelProperty(value = "活动时间，格式yyyy-MM-dd HH:mm", required = true)
    private String activityTime;
    @ApiModelProperty(value = "报名结束时间，格式yyyy-MM-dd HH:mm")
    private String enrollEndTime;
    @NotNull(message = "activityAddress is not null")
    @ApiModelProperty(value = "活动地址", required = true)
    private String activityAddress;
    @NotNull(message = "activityType is not null")
    @ApiModelProperty(value = "活动类型", required = true)
    private Integer activityType;
    @ApiModelProperty(value = "活动类型中文")
    private String activityTypeName;
    @NotNull(message = "needInfo is not null")
    @ApiModelProperty(value = "需要购票信息", required = true, allowableValues = "0 否 1 是")
    private int needInfo;
    @NotNull(message = "refundFlag is not null")
    @ApiModelProperty(value = "退票设置", required = true, allowableValues = "0 不可退, 1 可退")
    private int refundFlag;
    @NotNull(message = "state is not null")
    @ApiModelProperty(value = "状态", required = true, allowableValues = "0 删除, 1 草稿, 2发布")
    private int state;
    @ApiModelProperty(value = "定金，只有用户才有")
    private BigDecimal frontMoney;
    @ApiModelProperty(value = "图片url", notes = "多个用,分割（图片9张）")
    private String picUrl;
    @ApiModelProperty(value = "视频url", notes = "多个用,分割（1视频）")
    private String videoUrl;
    @ApiModelProperty(value = "内容")
    private String content;


    @ApiModelProperty(value = "设置票种")
    private List<TicketVO> ticketVoList;


}

