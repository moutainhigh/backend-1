package com.fb.feed.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    @TableField("clockin")
    private Integer clockin;

    /*打卡标签*/
    @TableField("clockin_tag")
    private String clockinTag;

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
    private Integer cityCode;

    /*市区码*/
    @TableField("ad_code")
    private Integer adCode;

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
    @TableField("create_time")
    private Date createTime;

    /*更新时间*/
    @TableField("update_time")
    private Date updateTime;


}
