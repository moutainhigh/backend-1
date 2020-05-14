package com.fb.user.service;

import com.fb.user.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

    @Resource
    private IUserService userService;
    @Test
    public void testUser() {
        System.out.println(userService.testUser());
    }

}
