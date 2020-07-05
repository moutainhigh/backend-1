package com.fb.user.service.impl;

import com.fb.user.repository.HobbyTagPO;
import com.fb.user.repository.HobbyTagRepository;
import com.fb.user.service.IHobbyTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: pangminpeng
 * @create: 2020-07-01 23:44
 */
@Service
public class HobbyTagServiceImpl implements IHobbyTagService {


    @Resource
    private HobbyTagRepository hobbyTagRepository;


    @Override
    public List<HobbyTagPO> listAllHobbyTag() {
        return hobbyTagRepository.listAllHobbyTag();
    }
}
