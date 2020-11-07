package com.fb.web.service;

import com.fb.addition.dto.LikeBO;
import com.fb.addition.service.ILikeService;
import com.fb.user.domain.AbstractUser;
import com.fb.web.entity.LikeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeFacadeService {

    @Autowired
    private ILikeService likeService;

    public boolean operatorLike(LikeVO likeVO, AbstractUser sessionUser) {

        return likeService.operatorLike(convertToBO(likeVO, sessionUser));
    }

    private LikeBO convertToBO(LikeVO likeVO, AbstractUser sessionUser) {
        LikeBO likeBO = new LikeBO();
        likeBO.setInfoId(likeVO.getInfoId());
        likeBO.setInfoType(likeVO.getInfoType());
        likeBO.setUserId(sessionUser.getUid());
        likeBO.setLikeState(likeVO.getState());
        likeBO.setAvatar(sessionUser.getHeadPicUrl());
        likeBO.setNickname(sessionUser.getName());
        return likeBO;

    }


}
