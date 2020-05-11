package com.fb.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Api("探熊")
public class TestController {

    @ResponseBody
    @GetMapping("/hello")
    @ApiOperation(value = "探熊", notes = "探熊api文档")
    public String hello(@ApiParam(name = "say", value = "说点啥吧") @RequestParam("say") String say) {
        return say;
    }
}
