package com.fb.message.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 动态表
 */
@Data
@TableName("tb_single_msg_history")
public class SingleMsgHistoryPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /*消息来源用户id*/
    @TableField("source_id")
    private Long sourceId;

    /*消息目标用户id*/
    @TableField("target_id")
    private Long targetId;

    /*消息类型 1 文本 2 语音 3 地理位置 4 视频 5 文件*/
    @TableField("msg_type")
    private Integer msgType;

    /*消息内容*/
    @TableField("msg_content")
    private String msgContent;

    /*序列id*/
    @TableField("seq_id")
    private Long seqId;

    /*创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
