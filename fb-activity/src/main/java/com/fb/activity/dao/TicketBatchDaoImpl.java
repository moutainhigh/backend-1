package com.fb.activity.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fb.activity.dao.mapper.TicketDao;
import com.fb.activity.entity.TicketPO;
import org.springframework.stereotype.Service;

@Service
public class TicketBatchDaoImpl extends ServiceImpl<TicketDao, TicketPO> implements TicketBatchDao {
}
