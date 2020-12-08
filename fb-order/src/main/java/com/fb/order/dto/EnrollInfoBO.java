package com.fb.order.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EnrollInfoBO {
    private Long id;

    /*用户id*/
    private Long userId;

    /*活动id*/
    private Long activityId;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

}
