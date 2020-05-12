package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Api(value = "活动模型")
public class ActivtyVo extends CommonVo {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "发布人id")
    private String userId;
    @ApiModelProperty(value = "活动标题")
    private String activtyTitle;
    @ApiModelProperty(value = "活动人数")
    private Integer numberCount;
    @ApiModelProperty(value = "活动时间，格式yyyy-MM-dd HH:mm", required = true)
    private String activtyTime;
    @ApiModelProperty(value = "报名结束时间，格式yyyy-MM-dd HH:mm" , required = true)
    private String enrollEndTime;
    @ApiModelProperty(value = "活动地址")
    private String activtyAddress;
    @ApiModelProperty(value = "活动类型")
    private Integer activtyType;
    @ApiModelProperty(value = "活动类型中文")
    private String activtyTypeName;
    @ApiModelProperty(value = "需要购票信息", required = true, allowableValues = "0 否 1 是")
    private int needInfo;
    @ApiModelProperty(value = "退票设置", required = true, allowableValues = "0 不可退, 1 可退")
    private int refundFlag;
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
    private List<TicketVo> ticketVoList;


}

