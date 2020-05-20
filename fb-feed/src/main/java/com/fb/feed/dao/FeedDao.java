package com.fb.feed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.feed.entity.FeedPO;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedDao extends BaseMapper<FeedPO> {
}
