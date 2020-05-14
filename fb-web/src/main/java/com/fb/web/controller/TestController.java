package com.fb.web.controller;

import com.fb.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private IUserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }


    @GetMapping("/testUser")
    public String testUser() {
        return userService.testUser();
    }
}
