package com.fb.relation.domian;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 关注关系
 */
public class FollowRelation {

    //关注者
    private Long userId1;

    //被关注者
    private Long userId2;

    private LocalDateTime createTime;
}
