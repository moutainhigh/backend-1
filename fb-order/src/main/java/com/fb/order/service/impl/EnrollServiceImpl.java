package com.fb.order.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fb.order.dao.EnrollDAO;
import com.fb.order.dto.EnrollInfoBO;
import com.fb.order.entity.EnrollPO;
import com.fb.order.service.EnrollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnrollServiceImpl implements EnrollService {

    @Autowired
    private EnrollDAO enrollDAO;

    @Override
    public boolean enrollActivity(Long userId, Long activityId, Long publishUserId) {
        EnrollPO enrollPO = new EnrollPO();
        enrollPO.setUserId(userId);
        enrollPO.setActivityId(activityId);
        enrollPO.setPublishUserId(publishUserId);

        int result = enrollDAO.insert(enrollPO);
        return result > 0;
    }

    @Override
    public List<EnrollInfoBO> queryEnrollList(Long userId, int limit, long offsetId) {

        QueryWrapper<EnrollPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(obj -> {
            obj.eq(EnrollPO::getUserId, userId);
            if (offsetId > 0) {
                obj.lt(EnrollPO::getId, offsetId);
            }
        }).orderByDesc(EnrollPO::getCreateTime);

        IPage<EnrollPO> enrollList = enrollDAO.selectPage(new Page(1, limit), queryWrapper);
        if (Objects.nonNull(enrollList)) {
            return enrollList.getRecords().stream().map(enrollPO -> convertToBO(enrollPO)).collect(Collectors.toList());
        }
        return null;
    }

    private EnrollInfoBO convertToBO(EnrollPO enrollPO) {
        EnrollInfoBO enrollInfoBO = new EnrollInfoBO();
        enrollInfoBO.setId(enrollPO.getId());
        enrollInfoBO.setUserId(enrollPO.getUserId());
        enrollInfoBO.setActivityId(enrollPO.getActivityId());
        enrollInfoBO.setCreateTime(enrollPO.getCreateTime());
        enrollInfoBO.setUpdateTime(enrollPO.getUpdateTime());
        return enrollInfoBO;

    }
}
