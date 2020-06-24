package com.fb.addition.service;

import com.fb.addition.dto.LikeBO;
import com.fb.addition.enums.InfoTypeEnum;

import java.util.List;
import java.util.Optional;

public interface ILikeService {

    /**
     * 点赞/取消赞
     * @param likeBO
     * @return
     */
    boolean operatorLike(LikeBO likeBO);

    /**
     * 点赞列表
     * @param infoId
     * @param infoType
     * @return
     */
    Optional<List<LikeBO>> getLikeList(Long infoId, InfoTypeEnum infoType);

    Optional<List<LikeBO>> getLikeListByInfoList(List<Long> infoIdList, InfoTypeEnum infoType);

}
