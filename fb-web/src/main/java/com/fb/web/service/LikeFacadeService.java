package com.fb.web.service;

import com.fb.addition.dto.LikeBO;
import com.fb.addition.service.ILikeService;
import com.fb.web.entity.LikeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LikeFacadeService {

    @Autowired
    private ILikeService likeService;

    public boolean operatorLike(LikeVO likeVO, Long userId) {
//TODO LX 根据uid获取用户信息

        return likeService.operatorLike(convertToBO(likeVO));
    }

    //TODO LX 补全
    private LikeBO convertToBO(LikeVO likeVO) {
        LikeBO likeBO = new LikeBO();
        likeBO.setInfoId(likeVO.getInfoId());
        likeBO.setInfoType(likeVO.getInfoType());
        likeBO.setUserId(123456L);
        likeBO.setLikeState(likeVO.getState());
//        likeBO.setAvatar();
        likeBO.setNickname("测试用户");
        return likeBO;

    }


}
