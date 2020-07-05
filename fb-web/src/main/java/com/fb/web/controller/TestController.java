package com.fb.web.controller;

import com.fb.message.MessageHandler;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Api("探熊")
public class TestController {

    @Resource
    private IUserService userService;

    @Resource
    private MessageHandler messageHandler;

    @GetMapping("/hello")
    @ApiOperation(value = "探熊", notes = "探熊api文档")
    public String hello(@ApiParam(name = "say", value = "说点啥吧") @RequestParam("say") String say) {
        return say;
    }


    @GetMapping("/getUserNameByToken/{token}")
    public String getNameByToken(@PathVariable("token") String token) {
        return userService.getUserNameByToken(token);
    }

    @GetMapping("/userLogin")
    public String getCurrentUserName(@RequestAttribute(name = "user") AbstractUser user) {
        //是否入驻用户的判断
        System.out.println(user.isMerchant());
        System.out.println(user instanceof CommonUser);
        return user.getName();
    }

    @GetMapping("/message/redis")
    public String getMessage() {
        return messageHandler.testMessage();
    }

}
