package com.fb.user.service.impl;

import com.fb.user.domin.AbstractUser;
import com.fb.user.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Value("${key1}")
    public String string;


    public String testUser() {
        System.out.println(string);
        return string;
    }

    public AbstractUser checkAndRefresh(String token) {
        return null;
    }
}
