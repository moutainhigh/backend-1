package com.fb.web.service;

import com.fb.addition.dto.CommentBO;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.addition.service.ICommentService;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IUserService;
import com.fb.web.entity.CommentVO;
import com.fb.web.entity.output.CommentDetailVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentFacadeService {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    public boolean publishComment(CommentVO commentParamVO, UserDTO sessionUser) {
        UserDTO toUserDTO = null;
        if (Objects.nonNull(commentParamVO.getToUserId())) {
            toUserDTO = userService.getUserByUid(commentParamVO.getToUserId());
        }
        return commentService.addComment(convertToBO(commentParamVO, sessionUser, toUserDTO));
    }

    public List<CommentDetailVO> getCommentListByPage(Long infoId, InfoTypeEnum infoType, Integer pageSize, Integer pageNum) {
        List<CommentDetailVO> result = Lists.newArrayList();
        Optional<List<CommentBO>> commentBOList = commentService.getCommentList(infoId, infoType, pageSize, pageNum);
        if (commentBOList.isPresent()) {
            result = commentBOList.get().stream().map(commentBO -> convertToVO(commentBO)).collect(Collectors.toList());
        }
        return result;
    }

    private CommentBO convertToBO(CommentVO commentParamVO,UserDTO sessionUser, UserDTO toUserDTO) {
        CommentBO commentBO = new CommentBO();
        commentBO.setInfoId(commentParamVO.getInfoId());
        commentBO.setInfoType(commentParamVO.getInfoType());
        commentBO.setUserId(sessionUser.getUid());
        commentBO.setAvatar(sessionUser.getHeadPicUrl());
        commentBO.setNickname(sessionUser.getName());
        if (Objects.nonNull(toUserDTO)) {
            commentBO.setToUserId(commentParamVO.getToUserId());
            commentBO.setToAvatar(toUserDTO.getHeadPicUrl());
            commentBO.setToNickname(toUserDTO.getName());
        }
        commentBO.setContent(commentParamVO.getContent());
        return commentBO;
    }

    private CommentDetailVO convertToVO(CommentBO commentBO) {
        CommentDetailVO commentDetailVO = new CommentDetailVO();
        commentDetailVO.setId(commentBO.getId());
        commentDetailVO.setInfoId(commentBO.getInfoId());
        commentDetailVO.setInfoType(commentBO.getInfoType());
        commentDetailVO.setUserId(commentBO.getUserId());
        commentDetailVO.setNickname(commentBO.getNickname());
        commentDetailVO.setToUserId(commentBO.getToUserId());
        commentDetailVO.setToNickname(commentBO.getToNickname());
        commentDetailVO.setContent(commentBO.getContent());
        commentDetailVO.setCommentTime(commentBO.getCreateTime().getTime());
//        commentDetailVO.setCommentTime(commentBO.getCreateTime().atZone(DateUtils.zoneId).toInstant().toEpochMilli());
        return commentDetailVO;
    }
}
