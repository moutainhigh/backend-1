package com.fb.message.dto.param;

import com.fb.message.enums.MsgTypeEnum;
import lombok.Data;

@Data
public class SingleMessageParam {

    /*消息来源用户id*/
    private Long sourceId;

    /*消息目标用户id*/
    private Long targetId;

    /*消息类型 1 文本 2 语音 3 地理位置 4 视频 5 文件*/
    private MsgTypeEnum msgType;

    /*消息内容*/
    private String msgContent;
}
