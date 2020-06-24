package com.fb.addition.service;

import com.fb.addition.dto.CommentBO;
import com.fb.addition.enums.InfoTypeEnum;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICommentService {

    /**
     * 获取帖子评论列表
     * @param infoId
     * @param infoType
     * @param pageSize
     * @param pageNum
     * @return
     */
    Optional<List<CommentBO>> getCommentList(Long infoId, InfoTypeEnum infoType, Integer pageSize, Integer pageNum);

    /**
     * 添加评论
     * @return
     */
    boolean addComment(CommentBO commentBO);

    /**
     * 删除评论
     * @return
     */
    boolean dropComment(Long id);

    /**
     * 评论数
     * @param infoId
     * @param infoType
     * @return
     */
    int getCommentCount(Long infoId, InfoTypeEnum infoType);

    /**
     * 评论数
     * @param infoId
     * @param infoType
     * @return
     */
    Optional<Map<Long, Long>> getCommentCountMap(List<Long> infoId, InfoTypeEnum infoType);


}
