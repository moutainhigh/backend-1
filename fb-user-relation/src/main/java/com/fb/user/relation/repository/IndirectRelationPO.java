package com.fb.user.relation.repository;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: pangminpeng
 * @create: 2020-06-22 23:14
 */
@Data
public class IndirectRelationPO {
    private Long id;

    private Long userId1;

    private Long userId2;

    private Byte level;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
