package com.fb.message;

import com.fb.common.util.RedisUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageHandler {

    @Resource
    @Qualifier("messageRedis")
    private RedisUtils redisUtils;

    public String testMessage() {
        redisUtils.setCacheObject("a", "b");
        return (String) redisUtils.getCacheObject("a");
    }
}
