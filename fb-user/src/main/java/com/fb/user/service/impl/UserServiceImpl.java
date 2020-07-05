package com.fb.user.service.impl;

import com.fb.common.util.RedisUtils;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.enums.SexEnum;
import com.fb.user.repository.UserRepository;
import com.fb.user.request.HobbyTagReq;
import com.fb.user.request.UserReq;
import com.fb.user.service.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.*;

@Service
public class UserServiceImpl implements IUserService {

    private final static int TOKEN_TIMEOUT = 3; //3天的过期时间


    public String string;

    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;
    @Resource
    private UserRepository userRepository;


    public AbstractUser checkAndRefresh(String token) {
        AbstractUser user = (AbstractUser) redisUtils.getCacheObject(token);
        if (Objects.isNull(user)) return null;
        redisUtils.setCacheObject(token, user, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return user;
    }

    @Override
    public String getUserNameByToken(String token) {
        AbstractUser user = (AbstractUser) redisUtils.getCacheObject(token);
        return user.getName();
    }

    @Override
    public CommonUser createUser(UserReq userReq) {
        CommonUser commonUser = new CommonUser();
        buildUserByReq(userReq, commonUser);
        return (CommonUser) userRepository.save(commonUser);
    }

    @Override
    public AbstractUser login(String phoneNumber) {
        AbstractUser user = userRepository.getOneUserByPhoneNumber(phoneNumber);
        if (Objects.isNull(user))
            return null;
        String token = user.getUid().toString() + System.currentTimeMillis();
        redisUtils.setCacheObject(token, user, TOKEN_TIMEOUT, TimeUnit.DAYS);
        user.setLoginToken(token);
        return user;
    }

    @Override
    public AbstractUser modifyUser(UserReq userReq) {
        AbstractUser lastUser = userRepository.getOneUserById(userReq.getUid());
        if (Objects.isNull(lastUser))
            throw new RuntimeException("未发现该用户");
        buildUserByReq(userReq, lastUser);
        userRepository.save(lastUser);
        redisUtils.setCacheObject(userReq.getLoginToken(), lastUser, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return lastUser;
    }

    private void buildUserByReq(UserReq userReq, AbstractUser lastUser) {
        lastUser.setAdCode(userReq.getAdCode());
        lastUser.setBirthday(LocalDate.parse(userReq.getBirthday(), ofPattern("yyyy-MM-dd")));
        lastUser.setCityCode(userReq.getCityCode());
        lastUser.setHeadPicUrl(userReq.getHeadPicUrl());
        lastUser.setHobbyTagList(userReq.getHobbyTagNameList().stream().distinct().collect(Collectors.toList()));
        lastUser.setIntroduction(userReq.getIntroduction());
        lastUser.setLat(userReq.getLat());
        lastUser.setLng(userReq.getLng());
        lastUser.setLocationStr(userReq.getLocationStr());
        lastUser.setName(userReq.getName());
        lastUser.setSex(SexEnum.getSexEnumByCode(userReq.getSex()));
        lastUser.setPhoneNumber(userReq.getPhoneNumber());
    }
}
