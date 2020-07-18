package com.fb.message.service;

import com.fb.message.dto.MsgNumBO;
import com.fb.message.dto.SingleMessageBO;
import com.fb.message.dto.param.SingleMessageParam;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    /**
     * 保存消息
     * @param singleMessageParam
     */
    void saveMsg(SingleMessageParam singleMessageParam);

    /**
     * 删除消息
     * @param targetId
     * @param sourceId
     */
    void deleteMsgByPair(long targetId, long sourceId);

    /**
     *获取未读单聊数量
     * @param targetId
     * @return
     */
    Optional<List<MsgNumBO>> queryMsgList(long targetId);

    /**
     * 查询消息
     * @param targetId
     * @param sourceId
     * @return
     */
    Optional<List<SingleMessageBO>> queryMsgByPair(long targetId, long sourceId);

    /**
     * 查询历史消息
     * @param targetId
     * @param sourceId
     * @return
     */
    Optional<List<SingleMessageBO>> queryHistoryMsgByPair(long targetId, long sourceId, int pageNo, int pageSize);


}
