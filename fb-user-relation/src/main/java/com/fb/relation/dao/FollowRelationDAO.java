package com.fb.relation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.relation.repository.FollowRelationPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-10-04 14:15
 */
@Repository
public interface FollowRelationDAO extends BaseMapper<FollowRelationPO> {

    @Select("select followed_user_id from follow_relation where user_id=#{uid,jdbcType=BIGINT}")
    List<Long> listFollowedUserId(Long uid);

    @Select("select user_id from follow_relation where followed_user_id=#{uid,jdbcType=BIGINT}")
    List<Long> listFansId(Long uid);

}
