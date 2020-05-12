package com.fb.web.controller;

import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/common")
@Api(value = "通用", description = "通用接口")
@Slf4j
public class CommonController {

    @ResponseBody
    @ApiOperation(value = "所有活动类型", notes = "字典")
    @RequestMapping(value = "/activtytype", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<Map<Integer, String>> getActivtyType() throws Exception {
        return JsonObject.newCorrectJsonObject("");
    }

    @ResponseBody
    @ApiOperation(value = "云配置", notes = "上传云配置（需要调研用到哪些配置，申请完之后再给）")
    @RequestMapping(value = "/uploadconfig", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public JsonObject<Object> getUploadYun() throws Exception {
        return JsonObject.newCorrectJsonObject("");
    }
}
