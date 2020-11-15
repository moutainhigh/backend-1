package com.fb.relation.domian;

import java.time.LocalDateTime;

/**
 * 以userId1为主的直接好友关系
 */

public class DirectFriendRelation {

    private Long userId1;

    private Long userId2;

    private LocalDateTime createTime;

    //关系权限
    private RelationAuthority relationAuthority;
}
