package com.fb.activity.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fb.activity.entity.ActivityPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ActivityBatchDAO extends ServiceImpl<ActivityDAO, ActivityPO> {
}
