package com.fb.user.repository;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.fb.user.dao.HobbyTagDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: pangminpeng
 * @create: 2020-07-04 21:56
 */
@Service
public class HobbyTagRepository {

    @Resource
    private HobbyTagDAO hobbyTagDAO;

    private final static Map<String, Integer> HOBBYTAG_MAP = new ConcurrentHashMap<>();


    public void insertIgnore(List<String> hobbyTagNameList) {
        hobbyTagDAO.batchInsertIgnore(hobbyTagNameList);
    }

    public List<HobbyTagPO> listAllHobbyTag() {
        return new LambdaQueryChainWrapper<HobbyTagPO>(hobbyTagDAO).list();
    }
}
