package com.fb.relation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.relation.repository.FollowRelationPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
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

    @Select("select count(1) from follow_relation where user_id=#{uid,jdbcType=BIGINT} and followed_user_id=#{targetUid,jdbcType=BIGINT}")
    int countByUidAndTargetUid(@Param("uid") Long uid, @Param("targetUid") Long targetUid);

    @Delete("delete from follow_relation where user_id=#{uid,jdbcType=BIGINT} and followed_user_id=#{targetUid,jdbcType=BIGINT}")
    int unfollow(@Param("uid") Long uid, @Param("targetUid") Long targetUid);

}
