package com.fb.addition.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fb.addition.dao.LikeDAO;
import com.fb.addition.dto.LikeBO;
import com.fb.addition.entity.LikePO;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.addition.enums.LikeStateEnum;
import com.fb.addition.service.ILikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private LikeDAO likeDao;

    /**
     * 点赞，取消点赞
     *
     * @param likeBO
     * @return
     */
    @Override
    public boolean operatorLike(LikeBO likeBO) {
        //根据唯一索引确定是否有这条赞
        LikePO like = this.selectLike(likeBO.getInfoId(), likeBO.getInfoType(), likeBO.getUserId());
        if (Objects.nonNull(like)) {
            if (!like.getLikeState().equals(likeBO.getLikeState())) {
                like.setAvatar(likeBO.getAvatar());
                like.setNickname(likeBO.getNickname());
                like.setLikeState(likeBO.getLikeState());
                likeDao.updateById(like);
            }
            return true;
        }
        //没有就添加
        LikePO likePO = new LikePO();
        BeanUtils.copyProperties(likeBO, likePO);
        return likeDao.insert(likePO) > 0 ? true : false;

    }

    private LikePO selectLike(Long infoId, Integer infoType, Long userId) {
        QueryWrapper<LikePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(LikePO::getInfoId, infoId);
            obj.eq(LikePO::getInfoType, infoType);
            obj.eq(LikePO::getUserId, userId);
        });
        return likeDao.selectOne(queryWrapper);
    }

    /**
     * 获取某个帖子点赞的列表
     *
     * @param infoId
     * @param infoType
     * @return
     */
    @Override
    public Optional<List<LikeBO>> getLikeList(Long infoId, InfoTypeEnum infoType) {

        QueryWrapper<LikePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(LikePO::getInfoId, infoId);
            obj.eq(LikePO::getInfoType, infoType.getCode());
            obj.eq(LikePO::getLikeState, LikeStateEnum.LIKE.getCode());

        });
        List<LikePO> likeOList = likeDao.selectList(queryWrapper);
        List<LikeBO> likeBOList = null;
        if (!CollectionUtils.isEmpty(likeOList)) {
            likeBOList = likeOList.stream().map(likePO -> {
                LikeBO likeBO = new LikeBO();
                BeanUtils.copyProperties(likePO, likeBO);
                return likeBO;
            }).collect(Collectors.toList());
        }

        return Optional.ofNullable(likeBOList);
    }

    @Override
    public Optional<List<LikeBO>> getLikeListByInfoList(List<Long> infoIdList, InfoTypeEnum infoType) {
        QueryWrapper<LikePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.in(LikePO::getInfoId, infoIdList);
            obj.eq(LikePO::getInfoType, infoType.getCode());
            obj.eq(LikePO::getLikeState, LikeStateEnum.LIKE.getCode());
        });
        List<LikePO> likeOList = likeDao.selectList(queryWrapper);
        List<LikeBO> likeBOList = null;
        if (!CollectionUtils.isEmpty(likeOList)) {
            likeBOList = likeOList.stream().map(likePO -> {
                LikeBO likeBO = new LikeBO();
                BeanUtils.copyProperties(likePO, likeBO);
                return likeBO;
            }).collect(Collectors.toList());
        }

        return Optional.ofNullable(likeBOList);
    }


}
