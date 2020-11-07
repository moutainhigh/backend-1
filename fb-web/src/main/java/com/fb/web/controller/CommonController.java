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
        Optional<UploadResult> picUploadResult = aliyunOssServiceImpl.uploadPicture(imgFile, userId);
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
    public static void sortColors(int[] nums) {

        int p = -1;
        int q = nums.length;
        int i = 0;
        while(i < q) {

             if (nums[i] == 0 ) {
                swap(nums, i,  ++ p);
            } if (nums[i] == 2) {
                swap(nums, i,   -- q);
            } if (nums[i] == 1 ) {
                i ++;
            }

        }
    }
    public static void swap(int[] nums, int p, int q) {
        int tmp=nums[p];
        nums[p]= nums[q];
        nums[q]= tmp;
    }

    public static void main(String[] args) {
//        int[] nums = {1,2,0};
        int[] nums = {2,0,2,1,1,0};
        sortColors(nums);
        System.out.println(JsonUtils.object2Json(nums));
    }

}
