package com.fb.feed.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 动态表
 */
@Data
@TableName("tb_feed")
public class FeedPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*打卡 0 否 1 是 默认0*/
    @TableField("clock_in")
    private Integer clockIn;

    /*打卡标签*/
    @TableField("clock_in_tag")
    private String clockInTag;

    /*发布到同城 0 否 1 是 默认0*/
    @TableField("display_city")
    private Integer displayCity;

    /*状态 0 删除 1 发布*/
    @TableField("feed_state")
    private Integer feedState;

    /*活动地址*/
    @TableField("feed_address")
    private String feedAddress;

    /*城市码*/
    @TableField("city_code")
    private String cityCode;

    /*城市名*/
    @TableField("city_name")
    private String cityName;

    /*市区码*/
    @TableField("ad_code")
    private String adCode;

    /*市区名*/
    @TableField("ad_name")
    private String adName;

    /*市区码*/
    @TableField("location")
    private String location;

    /*图片url*/
    @TableField("pic_url")
    private String picUrl;

    /*视频url*/
    @TableField("video_url")
    private String videoUrl;

    /*视频url*/
    @TableField("feed_content")
    private String feedContent;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
