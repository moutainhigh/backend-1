package com.fb.message.dto;

import com.fb.message.enums.MsgTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class SingleMessageBO {

    /*消息来源用户id*/
    private long sourceId;

    /*消息目标用户id*/
    private long targetId;

    /*消息类型 1 文本 2 语音 3 地理位置 4 视频 5 文件*/
    private MsgTypeEnum msgType;

    /*消息内容*/
    private String msgContent;

    /*序列id*/
    private long seqId;

    /*创建时间*/
    private Date createTime;
}
