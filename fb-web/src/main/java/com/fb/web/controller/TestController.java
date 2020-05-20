package com.fb.web.controller;
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

    @GetMapping("/hello")
    @ApiOperation(value = "探熊", notes = "探熊api文档")
    public String hello(@ApiParam(name = "say", value = "说点啥吧") @RequestParam("say") String say) {
        return say;
    }


    @GetMapping("/testUser")
    public String testUser() {
        return userService.testUser();
    }
}
