package com.fb.activity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ActivityBO {
    private Long id;

    /*用户id*/
    private Long userId;

    /*发布者类型 0 商家 1 用户*/
    private Integer userType;

    /*有效期 0 长期 1 短期*/
    private Integer activityValid;

    /*活动标题*/
    private String activityTitle;

    /*发活动人数*/
    private Integer memberCount;

    /*活动时间*/
    private Date activityTime;

    /*报名结束时间*/
    private Date enrollEndTime;

    /*活动地址*/
    private String activityAddress;

    /*城市码*/
    private String cityCode;

    /*市区码*/
    private String adCode;

    /*城市码*/
    private String cityName;

    /*市区码*/
    private String adName;

    /*定位*/
    private String location;

    /*活动类型 可配置见同名枚举*/
    private Integer activityType;

    /*需要购票信息 0 否 1 是*/
    private Integer needInfo;

    /*退票标识 0 不可退 1 可退*/
    private Integer refundFlag;

    /*状态 0 删除 1 草稿 2 发布 -1 停止报名*/
    private Integer activityState;

    /*定金*/
    private BigDecimal frontMoney;

    /*图片url*/
    private String picUrl;

    /*视频url*/
    private String videoUrl;

    /*活动内容*/
    private String activityContent;

    /*发布时间*/
    private Date updateTime;


    private List<TicketBO> ticketBOList;


}
