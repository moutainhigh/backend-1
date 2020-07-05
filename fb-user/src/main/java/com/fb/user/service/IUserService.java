package com.fb.user.service;

import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.request.UserReq;

public interface IUserService {



    /**
     * 检查并刷新用户token,检查通过，刷新过期时间；检查不通过，返回null
     * @param token
     * @return 返回当前用户的基本信息
     */
    AbstractUser checkAndRefresh(String token);

    /**
     * 用户注册，创建用户，新创建的用户只能是普通用户，返回普通用户
     * @param token
     * @return
     */
    CommonUser createUser(UserReq userReq);


    /**
     * web层校验完手机验证码，使用手机号查询用户是否存在，用户不存在返回null。
     * 用户存在则返回用户信息和token值回前端
     */
    AbstractUser login(String phoneNumber);

    /**
     * 用户修改基本信息
     * @param token
     * @return
     */
    AbstractUser modifyUser(UserReq userReq);

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
