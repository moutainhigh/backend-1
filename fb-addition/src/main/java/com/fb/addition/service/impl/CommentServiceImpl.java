package com.fb.addition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.addition.dao.CommentDAO;
import com.fb.addition.dto.CommentBO;
import com.fb.addition.entity.CommentPO;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.addition.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentDAO commentDao;

    @Override
    public Optional<List<CommentBO>> getCommentList(Long infoId, InfoTypeEnum infoType, Integer pageSize, Integer pageNum) {
        QueryWrapper<CommentPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(CommentPO::getInfoId, infoId);
            obj.eq(CommentPO::getInfoType, infoType.getCode());

        });

        IPage commentPages = commentDao.selectPage(new Page(pageNum, pageSize),queryWrapper);
        if (Objects.nonNull(commentPages)) {
            List<CommentPO> commentPOList = commentPages.getRecords();
            List<CommentBO> result = commentPOList.stream().map(commentPO -> {
                CommentBO commentBO = new CommentBO();
                BeanUtils.copyProperties(commentPO, commentBO);
                return commentBO;
            }).collect(Collectors.toList());
            return Optional.ofNullable(result);
        }
        return Optional.empty();
    }

    @Override
    public int getCommentCount(Long infoId, InfoTypeEnum infoType) {
        QueryWrapper<CommentPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(CommentPO::getInfoId, infoId);
            obj.eq(CommentPO::getInfoType, infoType.getCode());

        });
        return commentDao.selectList(queryWrapper).size();
    }

    @Override
    public Optional<Map<Long, Long>> getCommentCountMap(List<Long> infoId, InfoTypeEnum infoType) {
        QueryWrapper<CommentPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.in(CommentPO::getInfoId, infoId);
            obj.eq(CommentPO::getInfoType, infoType.getCode());

        });
        List<CommentPO> commentPOList = commentDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(commentPOList)) {
            return Optional.empty();
        }

        return Optional.ofNullable(commentPOList.stream().collect(Collectors.groupingBy(CommentPO::getInfoId, Collectors.counting())));

    }

    @Override
    public boolean addComment(CommentBO commentBO) {
        CommentPO commentPO = new CommentPO();
        BeanUtils.copyProperties(commentBO, commentPO);

        return commentDao.insert(commentPO) > 0 ? true : false;
    }

    @Override
    public boolean dropComment(Long id) {
        return commentDao.deleteById(id) > 0 ? true : false;
    }

}
