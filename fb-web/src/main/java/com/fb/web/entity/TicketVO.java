package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Api(value = "票种模型")
public class TicketVO {

    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "票种")
    private String ticketName;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    @ApiModelProperty(value = "拼团", allowableValues = "0 否, 1 是")
    private int assemble;
    @ApiModelProperty(value = "状态", allowableValues = "0 删除, 1 发布")
    private int ticketState;
    @ApiModelProperty(value = "拼团价格")
    private BigDecimal assemblePrice;
    @ApiModelProperty(value = "拼团人数")
    private Integer assembleMemberCount;//TODO assembleNumberCount
    @ApiModelProperty(value = "说明")
    private String illustration;

}
