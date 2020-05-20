package com.fb.feed.dto;

import lombok.Data;

@Data
public class FeedBO {

    /*用户id*/
    private Long userId;

    /*打卡 0 否 1 是 默认0*/
    private Integer clockin;

    /*打卡标签*/
    private String clockinTag;

    /*发布到同城 0 否 1 是 默认0*/
    private Integer displayCity;

    /*状态 0 删除 1 发布*/
    private Integer feedState;

    /*活动地址*/
    private String feedAddress;

    /*城市码*/
    private Integer cityCode;

    /*市区码*/
    private Integer adCode;

    /*图片url*/
    private String picUrl;

    /*视频url*/
    private String videoUrl;

    /*视频url*/
    private String content;


}
