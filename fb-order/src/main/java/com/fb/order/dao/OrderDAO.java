package com.fb.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fb.order.entity.OrderPO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDAO extends BaseMapper<OrderPO> {
}
