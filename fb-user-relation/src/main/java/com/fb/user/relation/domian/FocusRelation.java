package com.fb.user.relation.domian;

import java.time.LocalDateTime;

/**
 * 关注关系
 */
public class FocusRelation {

    //关注者
    private Long userId1;

    //被关注者
    private Long userId2;

    private LocalDateTime createTime;
}
