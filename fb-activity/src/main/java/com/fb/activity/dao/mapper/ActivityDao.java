package com.fb.activity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.activity.entity.ActivityPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends BaseMapper<ActivityPO> {

    @Select("SELECT * FROM tb_activity WHERE user_id = #{userId} AND activity_state = #{activityState} LIMIT 1")
    ActivityPO findByUserIdAndState(@Param("userId") long userId, @Param("activityState") int activityState);
}
