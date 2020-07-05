package com.fb.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.user.repository.HobbyTagPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-07-04 21:57
 */
@Repository
public interface HobbyTagDAO extends BaseMapper<HobbyTagPO> {

    int batchInsertIgnore(@Param("list") List<String> nameList);
}
