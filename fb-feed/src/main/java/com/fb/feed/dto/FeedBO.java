package com.fb.feed.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class FeedBO {
    private Long id;

    /*用户id*/
    private Long userId;

    /*打卡 0 否 1 是 默认0*/
    private Integer clockIn;

    /*打卡标签*/
    private String clockInTag;

    /*发布到同城 0 否 1 是 默认0*/
    private Integer displayCity;

    /*状态 0 删除 1 发布*/
    private Integer feedState;

    /*活动地址*/
    private String feedAddress;

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

    /*图片url*/
    private String picUrl;

    /*视频url*/
    private String videoUrl;

    /*视频url*/
    private String feedContent;

    /*发布时间*/
    private Date createTime;


}
