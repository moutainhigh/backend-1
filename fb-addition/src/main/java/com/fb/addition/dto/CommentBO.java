package com.fb.addition.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *评论
 */
@Data
public class CommentBO {

    private Long id;

    private Long infoId;

    private Integer infoType;

    private Long userId;

    private String avatar;

    private String nickname;

    private Long toUserId;

    private String toAvatar;

    private String toNickname;

    private String content;

    private Date createTime;
}
