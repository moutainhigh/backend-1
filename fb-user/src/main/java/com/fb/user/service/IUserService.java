package com.fb.user.service;

import com.fb.user.domain.AbstractUser;

public interface IUserService {



    /**
     * 检查并刷新用户token,
     * @param token
     * @return 返回当前用户
     */
    AbstractUser checkAndRefresh(String token);

    /**
     * 用户注册，创建用户
     * @param token
     * @return
     */
    AbstractUser createUser(AbstractUser user);


    /**
     * 用户登录，返回token
     */

    /**
     * 用户修改基本信息
     * @param token
     * @return
     */

    /**
     * 普通用户升级入驻
     * @param token
     * @return
     */

    /**
     *
     * @param token
     * @return
     */

    String getUserNameByToken(String token);
}
