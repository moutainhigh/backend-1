package com.fb.relation.repository;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: pangminpeng
 * @create: 2020-06-22 23:14
 */
@Data
@NoArgsConstructor
@TableName("direct_relation")
public class DirectRelationPO {

    public DirectRelationPO(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }
    public DirectRelationPO(Long userId1, Long userId2, LocalDateTime createTime) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.createTime = createTime;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id1")
    private Long userId1;

    @TableField("user_id2")
    private Long userId2;

    @TableField("create_time")
    private LocalDateTime createTime;
}
