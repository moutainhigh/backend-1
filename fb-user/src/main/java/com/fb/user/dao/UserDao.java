package com.fb.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.user.repository.UserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 复杂的sql在userDao里写接口，在xml中写sql实现
 */

@Repository
public interface UserDao extends BaseMapper<UserPO> {

    List<UserPO> getUserByIdList();
}
