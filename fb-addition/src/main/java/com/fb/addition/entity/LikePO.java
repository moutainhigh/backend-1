package com.fb.addition.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 点赞
 */
@Data
@TableName("tb_like")
public class LikePO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("info_id")
    private Long infoId;

    /*状态 1 活动 2 动态*/
    @TableField("info_type")
    private Integer infoType;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*状态 0 取消 1 点赞*/
    @TableField("like_state")
    private Integer likeState;

    /*用户头像*/
    @TableField("avatar")
    private String avatar;

    /*用户昵称*/
    @TableField("nickname")
    private String nickname;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
