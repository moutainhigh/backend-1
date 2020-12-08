package com.fb.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_enroll")
public class EnrollPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*用户id*/
    @TableField("user_id")
    private Long userId;

    /*产品id*/
    @TableField("activity_id")
    private Long activityId;

    /*发布人uid*/
    @TableField("publish_user_id")
    private Long publishUserId;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /*更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
