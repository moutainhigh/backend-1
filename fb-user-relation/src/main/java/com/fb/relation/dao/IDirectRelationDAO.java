package com.fb.relation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.relation.repository.DirectRelationPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-06-22 23:20
 */
@Repository
public interface IDirectRelationDAO extends BaseMapper<DirectRelationPO> {

    int batchInsert(List<DirectRelationPO> list);

    @Delete("delete from direct_relation where (user_id1=#{directRelationPO.userId1,jdbcType=BIGINT} and user_id2=#{directRelationPO.userId2,jdbcType=BIGINT})" +
            " or (user_id1=#{directRelationPO.userId2,jdbcType=BIGINT} and user_id2=#{directRelationPO.userId1,jdbcType=BIGINT})")
    int deleteRelation(@Param("directRelationPO") DirectRelationPO directRelationPO);
//
//    @Delete("delete from direct_relation where user_id1=#{userId1,jdbcType=BIGINT} and user_id2=#{userId2,jdbcType=BIGINT}")
//    int deleteRelation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

//    @Select("select user_id1 from direct_relation where user_id2=#{uid,jdbcType=BIGINT} union all " +
//            "select user_id2 from direct_relation where user_id1=#{uid,jdbcType=BIGINT}")
//    List<Long> listDirectFriendId(Long uid);

    @Select("select user_id2 from direct_relation where user_id1=#{uid,jdbcType=BIGINT}")
    List<Long> listDirectFriendId(Long uid);

}
