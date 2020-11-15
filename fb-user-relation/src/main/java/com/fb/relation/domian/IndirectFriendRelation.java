package com.fb.relation.domian;

import java.time.LocalDateTime;

/**
 * 以userId1为主的间接好友关系
 */
public class IndirectFriendRelation {

    private Long relationId;

    private Long userId1;

    private Long userId2;

    //中间的好友

    //关系等级：2，3
    private Byte level;

    private RelationAuthority relationAuthority;


    private LocalDateTime createTime;
}
