package com.fb.activity.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fb.activity.dao.mapper.ActivityDao;
import com.fb.activity.entity.ActivityPO;
import org.springframework.stereotype.Service;

@Service
public class ActivityBatchDaoImpl extends ServiceImpl<ActivityDao, ActivityPO> implements ActivityBatchDao {
}
