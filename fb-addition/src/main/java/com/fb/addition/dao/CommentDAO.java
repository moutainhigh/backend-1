package com.fb.addition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.addition.entity.CommentPO;
import com.fb.addition.entity.LikePO;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDAO extends BaseMapper<CommentPO> {
}
