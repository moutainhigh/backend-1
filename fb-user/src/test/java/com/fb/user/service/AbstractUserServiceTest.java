package com.fb.user.service;

import com.fb.common.util.RedisUtils;
import com.fb.user.BaseTest;
import com.fb.user.domain.CommonUser;
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
    }

    @Test
    public void testRedis() {
        CommonUser user = new CommonUser();
        String token = "oneToken";

        CommonUser selectUser = (CommonUser) redisUtils.getCacheObject(token);
        System.out.println(selectUser.getName());
    }

}
