package com.fb.user.repository;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: pangminpeng
 * @create: 2020-07-04 21:51
 */
@Data
@TableName("hobby_tag")
public class HobbyTagPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("create_time")
    private LocalDateTime createTime;
}
