package com.fb.message.service.impl;
import com.fb.message.dto.MsgNumBO;
import com.fb.message.dto.SingleMessageBO;
import com.fb.message.enums.MsgTypeEnum;

import com.fb.message.BaseTest;
import com.fb.message.dto.param.SingleMessageParam;
import com.fb.message.service.IMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MessageServiceImplTest extends BaseTest {
    @Autowired
    private IMessageService messageService;

    public static final Long sourceId=5211234567897L;

    public static final Long targetId=131432143434549L;


    @Test
    public void saveMsg() {
        SingleMessageParam param = new SingleMessageParam();
        param.setSourceId(sourceId);
        param.setTargetId(targetId);
        param.setMsgType(MsgTypeEnum.TEXT);
        param.setMsgContent("00");

        /*SingleMessageParam param1 = new SingleMessageParam();
        param1.setSourceId(sourceId);
        param1.setTargetId(targetId);
        param1.setMsgType(MsgTypeEnum.VIDEO);
        param1.setMsgContent("https://test");*/

        messageService.saveMsg(param);
//        messageService.saveMsg(param1);

    }

    @Test
    public void queryMsgList() {
        Optional<List<MsgNumBO>> msgNumBOList = messageService.queryMsgList(targetId);
        System.out.println(msgNumBOList.get());

    }

    @Test
    public void queryMsgByPair() {
        Optional<List<SingleMessageBO>> msgNumBOList = messageService.queryMsgByPair(targetId, sourceId);
        System.out.println(msgNumBOList.get());
    }

    @Test
    public void deleteMsgByPair() {
        messageService.deleteMsgByPair(targetId, sourceId);
    }

    @Test
    public void queryHistoryMsgByPair() {
        Optional<List<SingleMessageBO>> historyMsgByPair =  messageService.queryHistoryMsgByPair(targetId, sourceId,1,5);
        System.out.println(historyMsgByPair.get());

    }
}
