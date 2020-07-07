package com.fb.web.controller;

import com.fb.activity.enums.ActivityTypeEnum;
import com.fb.common.model.UploadResult;
import com.fb.common.service.OssService;
import com.fb.web.entity.output.UploadVo;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javaslang.Tuple;
import javaslang.Tuple3;
import javaslang.Tuple4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public JsonObject<UploadVo> getUploadImage(@RequestParam @ApiParam("图片文件") MultipartFile imgFile) {

        long uid = 123456L;
        Optional<UploadResult> picUploadResult = aliyunOssServiceImpl.uploadPicture(imgFile, uid);
        UploadVo uploadVo = new UploadVo();
        BeanUtils.copyProperties(picUploadResult.get(), uploadVo);
        return JsonObject.newCorrectJsonObject(uploadVo);
    }
    @ResponseBody
    @ApiOperation(value = "视频上传云", notes = "视频上传云")
    @RequestMapping(value = "/uploadVideo", method = {RequestMethod.POST})
    public JsonObject<UploadVo> getUploadVideo(@RequestParam @ApiParam("视频文件") MultipartFile videoFile) {

        long uid = 123456L;
        Optional<UploadResult> videoUploadResult = aliyunOssServiceImpl.uploadVideo(videoFile, uid);
        UploadVo uploadVo = new UploadVo();
        BeanUtils.copyProperties(videoUploadResult.get(), uploadVo);
        return JsonObject.newCorrectJsonObject(uploadVo);
    }

    @ResponseBody
    @ApiOperation(value = "图片删除", notes = "图片删除")
    @RequestMapping(value = "/deleteImage", method = {RequestMethod.POST})
    public JsonObject<Boolean> deleteImage(@RequestParam @ApiParam("图片") String url) {

        long uid = 123456L;
        boolean deletePictureResult = aliyunOssServiceImpl.deleteFile(url, uid);
        return JsonObject.newCorrectJsonObject(deletePictureResult);
    }
}
