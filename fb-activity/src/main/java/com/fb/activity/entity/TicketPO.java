package com.fb.activity.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("tb_ticket")
public class TicketPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*活动表主键*/
    @TableField("activity_id")
    private Long activityId;

    /*票种*/
    @TableField("ticket_name")
    private String ticketName;

    /*价格*/
    @TableField("ticket_price")
    private BigDecimal ticketPrice;

    /*拼团 0 否 1 是*/
    @TableField("assemble")
    private Integer assemble;

    /*拼团价格*/
    @TableField("assemble_price")
    private BigDecimal assemblePrice;

    /*活动人数*/
    @TableField("assemble_member_count")
    private Integer assembleMemberCount;

    /*状态 0 删除 1 发布*/
    @TableField("ticket_state")
    private Integer ticketState;

    /*说明*/
    @TableField("illustration")
    private String illustration;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
