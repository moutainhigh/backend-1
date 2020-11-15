package com.fb.user.relation.repository;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: pangminpeng
 * @create: 2020-10-04 14:18
 */
@Data
@TableName("follow_relation")
public class FollowRelationPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //当前用户id
    @TableField("user_id")
    private Long userId;

    //当前用户关注的用户id
    @TableField("followed_user_id")
    private Long followedUserId;

    @TableField("create_time")
    private LocalDateTime createTime;
}
