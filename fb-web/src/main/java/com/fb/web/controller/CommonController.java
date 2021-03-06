package com.fb.web.controller;

import com.fb.activity.enums.ActivityTypeEnum;
import com.fb.common.model.UploadResult;
import com.fb.common.service.OssService;
import com.fb.common.util.JsonUtils;
import com.fb.user.response.UserDTO;
import com.fb.web.entity.output.UploadVo;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javaslang.Tuple;
import javaslang.Tuple3;
import javaslang.Tuple4;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/common", produces = "application/json;charset=UTF-8")
@Api(value = "通用", description = "通用接口")
@Slf4j

public class CommonController {
    @Autowired
    private OssService aliyunOssServiceImpl;

    private final static String PIC_AUTH = "auth";
    private final static String PIC_NO_AUTH = "no_auth";

    @ResponseBody
    @ApiOperation(value = "所有活动类型", notes = "字典")
    @RequestMapping(value = "/activitytype", method = {RequestMethod.GET})
    public JsonObject<List<Tuple4<Integer, String, Integer, String>>> getActivityType() {
        List list = new ArrayList();
        for (ActivityTypeEnum activityTypeEnum : ActivityTypeEnum.values()) {
            list.add(Tuple.of(activityTypeEnum.getCode(), activityTypeEnum.getValue(), activityTypeEnum.getActivityValid(), activityTypeEnum.getNameByActivityValid(activityTypeEnum.getActivityValid())));
        }
        return JsonObject.newCorrectJsonObject(list);
    }

    @ResponseBody
    @ApiOperation(value = "图片上传云", notes = "图片上传云")
    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST})
    public JsonObject<UploadVo> getUploadImage(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                               @RequestParam @ApiParam("图片文件") MultipartFile imgFile) {
        Long userId = sessionUser.getUid();
        Optional<UploadResult> picUploadResult = aliyunOssServiceImpl.uploadPicture(imgFile, userId, PIC_AUTH);
        UploadVo uploadVo = new UploadVo();
        BeanUtils.copyProperties(picUploadResult.get(), uploadVo);
        return JsonObject.newCorrectJsonObject(uploadVo);
    }

    @ResponseBody
    @ApiOperation(value = "图片上传云", notes = "图片上传云，注册前调用的上传接口")
    @PostMapping(value = "/uploadImageNoAuth")
    public JsonObject<UploadVo> uploadImageNoAuth(@RequestParam @ApiParam("图片文件") MultipartFile imgFile) {
        Optional<UploadResult> picUploadResult = aliyunOssServiceImpl.uploadPicture(imgFile, Thread.currentThread().getId(), PIC_NO_AUTH);
        UploadVo uploadVo = new UploadVo();
        BeanUtils.copyProperties(picUploadResult.get(), uploadVo);
        return JsonObject.newCorrectJsonObject(uploadVo);
    }

    @ResponseBody
    @ApiOperation(value = "视频上传云", notes = "视频上传云")
    @RequestMapping(value = "/uploadVideo", method = {RequestMethod.POST})
    public JsonObject<UploadVo> getUploadVideo(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                               @RequestParam @ApiParam("视频文件") MultipartFile videoFile) {

        Long userId = sessionUser.getUid();
        Optional<UploadResult> videoUploadResult = aliyunOssServiceImpl.uploadVideo(videoFile, userId);
        UploadVo uploadVo = new UploadVo();
        BeanUtils.copyProperties(videoUploadResult.get(), uploadVo);
        return JsonObject.newCorrectJsonObject(uploadVo);
    }

    @ResponseBody
    @ApiOperation(value = "图片删除", notes = "图片删除")
    @RequestMapping(value = "/deleteImage", method = {RequestMethod.POST})
    public JsonObject<Boolean> deleteImage(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                           @RequestParam @ApiParam("图片") String url) {

        Long userId = sessionUser.getUid();
        boolean deletePictureResult = aliyunOssServiceImpl.deleteFile(url, userId);
        return JsonObject.newCorrectJsonObject(deletePictureResult);
    }

}
