package com.fb.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.common.util.RedisUtils;
import com.fb.message.dao.SingleMsgHistoryBatchDAO;
import com.fb.message.dao.SingleMsgHistoryDAO;
import com.fb.message.dto.MsgNumBO;
import com.fb.message.dto.SingleMessageBO;
import com.fb.message.dto.param.SingleMessageParam;
import com.fb.message.entity.SingleMsgHistoryPO;
import com.fb.message.enums.MsgTypeEnum;
import com.fb.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements IMessageService {
    /*单聊消息*/
    private static final String SINGLE_MSG = "single_msg_";
    /*单聊seq*/
    private static final String SINGLE_SEQ = "single_seq_";
    /*单聊消息列表*/
    private static final String SINGLE_LIST = "single_list_";

    @Autowired
    private SingleMsgHistoryDAO singleMsgHistoryDAO;

    @Autowired
    private SingleMsgHistoryBatchDAO singleMsgHistoryBatchDAO;

    @Resource
    @Qualifier("messageRedis")
    private RedisUtils redisUtils;

    /**
     * 保存单聊信息
     *
     * @param singleMessageParam
     * @return
     */
    @Override
    public void saveMsg(SingleMessageParam singleMessageParam) {
        //保存单聊消息
        String singleMsgKey = getSingleMsgKey(singleMessageParam.getTargetId(), singleMessageParam.getSourceId());
        redisUtils.rightPush(singleMsgKey, convertPOFromParam(singleMessageParam, getSeqId(singleMessageParam.getTargetId())));
        //保存列表中的人
        String singleListKey = getSingleListKey(singleMessageParam.getTargetId());
        redisUtils.addSetsCache(singleListKey, singleMessageParam.getSourceId());
    }

    /**
     * 获取未读单聊数量
     * @param targetId
     * @return
     */
    @Override
    public Optional<List<MsgNumBO>> queryMsgList(long targetId) {
        Set<Long> cacheList = redisUtils.getSetsCache(getSingleListKey(targetId));

        if (CollectionUtils.isEmpty(cacheList)) {
            return Optional.empty();
        }
        List<MsgNumBO> msgNumBOList = new ArrayList<>();
        cacheList.forEach(cache->{
            //查询redis单聊消息
            String singleMsgKey = getSingleMsgKey(targetId, cache);
            Long count = redisUtils.listSize(singleMsgKey);

            MsgNumBO msgNumBO = new MsgNumBO();
            msgNumBO.setSourceId(cache);
            msgNumBO.setCount(count);
            msgNumBOList.add(msgNumBO);
        });

        return Optional.ofNullable(msgNumBOList);
    }

    /**
     * 查询单聊的聊天的消息内容
     * @param targetId
     * @param sourceId
     * @return
     */
    @Override
    public Optional<List<SingleMessageBO>> queryMsgByPair(long targetId, long sourceId) {
        //查询redis单聊消息
        List<SingleMsgHistoryPO> singleMsgHistoryPOCache = getSingleMsgHistoryPOCache(targetId, sourceId);
        if (CollectionUtils.isEmpty(singleMsgHistoryPOCache)) {
            return Optional.empty();
        }
        return Optional.ofNullable(singleMsgHistoryPOCache.stream().map(msg->convertBOFromPO(msg)).collect(Collectors.toList()));
    }

    /**
     * 发送成功收到客户端ack删除消息和消息列表
     * @param targetId
     * @param sourceId
     */
    @Override
    public void deleteMsgByPair(long targetId, long sourceId) {
        //查询redis单聊消息
        List<SingleMsgHistoryPO> singleMsgHistoryPOCache = getSingleMsgHistoryPOCache(targetId, sourceId);
        //保存历史消息
        singleMsgHistoryBatchDAO.saveBatch(singleMsgHistoryPOCache);
        //删除列表
        redisUtils.delSetsCache(getSingleListKey(targetId), sourceId);
        //删除消息
        redisUtils.del(getSingleMsgKey(targetId, sourceId).getBytes());

    }

    /**
     * 查询历史消息
     * @param targetId
     * @param sourceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Optional<List<SingleMessageBO>> queryHistoryMsgByPair(long targetId, long sourceId, int pageNo, int pageSize) {

        QueryWrapper<SingleMsgHistoryPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(SingleMsgHistoryPO::getCreateTime);

        IPage<SingleMsgHistoryPO> singleMsgHistoryPOIPages = singleMsgHistoryDAO.selectPage(new Page(pageNo, pageSize), queryWrapper);
        if (Objects.nonNull(singleMsgHistoryPOIPages) && !CollectionUtils.isEmpty(singleMsgHistoryPOIPages.getRecords())) {
            return Optional.of(singleMsgHistoryPOIPages.getRecords().stream().map(singleMsgHistoryPO -> convertBOFromPO(singleMsgHistoryPO)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    /**
     * 获取seqId
     *
     * @param targetId
     * @return
     */
    private long getSeqId(long targetId) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(SINGLE_SEQ)
                .append(targetId);
        return redisUtils.increment(stringBuffer.toString(), 1);
    }

    /**
     * 获取单聊消息
     *
     * @param targetId
     * @param sourceId
     * @return
     */
    private String getSingleMsgKey(long targetId, long sourceId) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(SINGLE_MSG)
                .append(sourceId)
                .append("_")
                .append(targetId);
        return stringBuffer.toString();
    }

    /**
     * 获取单聊列表
     *
     * @param targetId
     * @return
     */
    private String getSingleListKey(long targetId) {
        StringBuffer stringBuffer = new StringBuffer()
                .append(SINGLE_LIST)
                .append(targetId);
        return stringBuffer.toString();
    }

    private List<SingleMsgHistoryPO> getSingleMsgHistoryPOCache(long targetId, long sourceId) {
        String singleMsgKey = getSingleMsgKey(targetId, sourceId);
        List<Object> cacheList = redisUtils.range(singleMsgKey, 0, -1);
        if (CollectionUtils.isEmpty(cacheList)) {
            return null;
        }
        List<SingleMsgHistoryPO> SingleMsgList = cacheList.stream().map(msg -> (SingleMsgHistoryPO) msg).collect(Collectors.toList());
        return SingleMsgList;
    }

    private SingleMsgHistoryPO convertPOFromParam(SingleMessageParam singleMessageParam, long seqId) {
        SingleMsgHistoryPO singleMsgHistoryPO = new SingleMsgHistoryPO();
        singleMsgHistoryPO.setSourceId(singleMessageParam.getSourceId());
        singleMsgHistoryPO.setTargetId(singleMessageParam.getTargetId());
        singleMsgHistoryPO.setMsgType(singleMessageParam.getMsgType().getCode());
        singleMsgHistoryPO.setMsgContent(singleMessageParam.getMsgContent());
        singleMsgHistoryPO.setSeqId(seqId);
        singleMsgHistoryPO.setCreateTime(new Date());
        return singleMsgHistoryPO;
    }

    private SingleMessageBO convertBOFromPO(SingleMsgHistoryPO singleMsgHistoryPO) {
        SingleMessageBO singleMessageBO = new SingleMessageBO();
        singleMessageBO.setSourceId(singleMsgHistoryPO.getSourceId());
        singleMessageBO.setTargetId(singleMsgHistoryPO.getTargetId());
        singleMessageBO.setMsgType(MsgTypeEnum.getMsgTypeEnumByCode(singleMsgHistoryPO.getMsgType()));
        singleMessageBO.setMsgContent(singleMsgHistoryPO.getMsgContent());
        singleMessageBO.setSeqId(singleMsgHistoryPO.getSeqId());
        singleMessageBO.setCreateTime(singleMsgHistoryPO.getCreateTime());
        return singleMessageBO;
    }

}
