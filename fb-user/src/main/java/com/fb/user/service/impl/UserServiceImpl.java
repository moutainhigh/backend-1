package com.fb.user.service.impl;

import com.fb.common.util.RedisUtils;
import com.fb.user.domin.AbstractUser;
import com.fb.user.service.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Value("${key1}")
    public String string;

    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;


    public String testUser() {
        System.out.println(string);
        return string;
    }

    public boolean setUser(String token, AbstractUser user) {
        redisUtils.setCacheObject(token, user);
        return true;
    }



    public AbstractUser checkAndRefresh(String token) {
        return null;
    }

    @Override
    public String getUserNameByToken(String token) {
        AbstractUser user = (AbstractUser) redisUtils.getCacheObject(token);
        return user.getName();
    }
}
