package com.fb.activity.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fb.activity.entity.TicketPO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TicketBatchDAO extends ServiceImpl<TicketDAO, TicketPO> {
}
