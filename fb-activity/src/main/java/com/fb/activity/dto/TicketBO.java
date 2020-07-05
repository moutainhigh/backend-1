package com.fb.activity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TicketBO {

    private Long id;

    /*票种*/
    private String ticketName;

    /*价格*/
    private BigDecimal ticketPrice;

    /*拼团 0 否 1 是*/
    private Integer assemble;

    /*拼团价格*/
    private BigDecimal assemblePrice;

    /*活动人数*/
    private Integer assembleMemberCount;

    /*状态 0 删除 1 发布*/
    private Integer ticketState;

    /*说明*/
    private String illustration;

    /*说明*/
    private Date createTime;


}
