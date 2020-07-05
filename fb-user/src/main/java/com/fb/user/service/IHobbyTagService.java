package com.fb.user.service;

import com.fb.user.repository.HobbyTagPO;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-06-10 23:33
 */
public interface IHobbyTagService {

    /**
     * 获取所有的爱好标签
     * @return
     */
    List<HobbyTagPO> listAllHobbyTag();
}
