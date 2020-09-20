package com.fb.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.order.entity.OrderUserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderUserDAO extends BaseMapper<OrderUserPO> {
}
