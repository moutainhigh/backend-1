package com.fb.user.service;

import com.fb.common.util.RedisUtils;
import com.fb.user.BaseTest;
import com.fb.user.domin.CommonUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

public class AbstractUserServiceTest extends BaseTest {

    @Resource
    private IUserService userService;

    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;


    @Test
    public void testUser() {
        System.out.println(userService.testUser());
    }

    @Test
    public void testRedis() {
        CommonUser user = new CommonUser();
        String token = "oneToken";
        userService.setUser(token, user);

        CommonUser selectUser = (CommonUser) redisUtils.getCacheObject(token);
        System.out.println(selectUser.getName());
    }

}
