package com.fb.addition.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *评论
 */
@Data
@TableName("tb_comment")
public class CommentPO {
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

    /*用户头像*/
    @TableField("avatar")
    private String avatar;

    /*用户昵称*/
    @TableField("nickname")
    private String nickname;

    /*用户id*/
    @TableField("to_user_id")
    private Long toUserId;

    /*用户头像*/
    @TableField("to_avatar")
    private String toAvatar;

    /*用户昵称*/
    @TableField("to_nickname")
    private String toNickname;

    /*内容*/
    @TableField("content")
    private String content;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
