package com.fb.user.service;

import com.fb.user.domain.HobbyTag;

import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-06-10 23:33
 */
public interface IHobbyTagService {

    /**
     * 添加一个爱好标签
     * @param tagName
     * @return
     */
    Boolean addHobbyTag(String tagName);

    /**
     * 获取所有的爱好标签
     * @return
     */
    List<HobbyTag> listAllHobbyTag();
}
