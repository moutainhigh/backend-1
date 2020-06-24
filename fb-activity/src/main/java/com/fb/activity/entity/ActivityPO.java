package com.fb.activity.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 活动表
 */
@Data
@TableName("tb_activity")
public class ActivityPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*发布者类型 0 商家 1 用户*/
    @TableField("user_type")
    private Integer userType;

    /*有效期 0 长期 1 短期*/
    @TableField("activity_valid")
    private Integer activityValid;

    /*活动标题*/
    @TableField("activity_title")
    private String activityTitle;

    /*发活动人数*/
    @TableField("member_count")
    private Integer memberCount;

    /*活动时间*/
    @TableField("activity_time")
    private Date activityTime;

    /*报名结束时间*/
    @TableField("enroll_end_time")
    private Date enrollEndTime;

    /*活动地址*/
    @TableField("activity_address")
    private String activityAddress;

    /*城市码*/
    @TableField("city_code")
    private String cityCode;

    /*城市码中文*/
    @TableField("city_name")
    private String cityName;

    /*市区码*/
    @TableField("ad_code")
    private String adCode;

    /*市区码中文*/
    @TableField("ad_name")
    private String adName;

    /*定位*/
    @TableField("location")
    private String location;

    /*活动类型 可配置见同名枚举*/
    @TableField("activity_type")
    private Integer activityType;

    /*需要购票信息 0 否 1 是*/
    @TableField("need_info")
    private Integer needInfo;

    /*退票标识 0 不可退 1 可退*/
    @TableField("refund_flag")
    private Integer refundFlag;

    /*状态 0 删除 1 草稿 2 发布*/
    @TableField("activity_state")
    private Integer activityState;

    /*定金*/
    @TableField("front_money")
    private BigDecimal frontMoney;

    /*图片url*/
    @TableField("pic_url")
    private String picUrl;

    /*视频url*/
    @TableField("video_url")
    private String videoUrl;

    /*活动内容*/
    @TableField("activity_content")
    private String activityContent;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
