package com.fb.user.service;

import com.fb.user.domin.AbstractUser;

public interface IUserService {

    String testUser();

    /**
     *
     */
    boolean setUser(String token, AbstractUser user);

    /**
     * 检查并刷新用户token
     * @param token
     * @return 返回当前用户
     */
    AbstractUser checkAndRefresh(String token);
}
