package com.fb.message.dto;

import lombok.Data;

@Data
public class MsgNumBO {
    /*发送人*/
    private long sourceId;
    /*数量*/
    private long count;
}
