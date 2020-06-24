package com.fb.addition.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 点赞
 */
@Data
public class LikeBO {

    private Long infoId;

    /*状态 1 活动 2 动态*/
    private Integer infoType;

    /*用户id*/
    private Long userId;

    /*状态 0 取消 1 点赞*/
    private Integer likeState;

    /*用户头像*/
    private String avatar;

    /*用户昵称*/
    private String nickname;

}
