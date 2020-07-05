package com.fb.web.controller;

import com.fb.common.util.CmsUtils;
import com.fb.common.util.RedisUtils;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.repository.HobbyTagPO;
import com.fb.user.request.UserReq;
import com.fb.user.service.IHobbyTagService;
import com.fb.user.service.IUserService;
import com.fb.web.entity.UserVO;
import com.fb.web.exception.UserResponse;
import com.fb.web.utils.JsonObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private IHobbyTagService hobbyTagService;
    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;

    private CmsUtils cmsUtils = new CmsUtils(redisUtils);
    //请求验证码接口, todo 验证手机号是否有效
    public JsonObject getVerifyCode(String phoneNumber) {
        if (cmsUtils.sendVerifyCode(phoneNumber)){
            return new JsonObject();
        }
        return JsonObject.newErrorJsonObject(UserResponse.SEND_VERIFYCODE_ERROR);
    }

    //验证码 + 手机号进行验证：登录成功，返回token值；用户未注册，前端进入注册页面
    public JsonObject<UserVO> verify(String phoneNumber, String verifyCode) {
        //todo 手机验证码的校验
        boolean checkResult = cmsUtils.checkVerifyCode(phoneNumber, verifyCode);
        if (checkResult) {
            AbstractUser user = userService.login(phoneNumber);
            if (Objects.isNull(user)) {
                //返回不存在该用户，直接注册用户
                return JsonObject.newErrorJsonObject(UserResponse.USER_NOT_EXIST);
            }
            UserVO userVO = new UserVO(user);
            return new JsonObject<>(userVO);
        }
        return JsonObject.newErrorJsonObject(UserResponse.VERIFY_FAIL);
    }

    //注册，注册之后也应该把详细信息返回到设备端
    public JsonObject<UserVO> signUp(@RequestBody UserReq userReq) {
        CommonUser user = userService.createUser(userReq);
        UserVO userVO = new UserVO(user);
        return new JsonObject<>(userVO);
    }

    //获取爱好标签接口
    @GetMapping("/listHobbyTagName")
    public JsonObject<List<HobbyTagPO>> listHobbyTag() {
        return new JsonObject<>(hobbyTagService.listAllHobbyTag());
    }


    //修改用户基本信息接口，是否
    public JsonObject<UserVO> modifyUser(@RequestBody UserReq userReq) {
        AbstractUser user = userService.modifyUser(userReq);
        return new JsonObject<>(new UserVO(user));
    }

    //用户的基本信息获取
    public JsonObject<UserVO> getBasicInfoByToken(@RequestAttribute(name = "user")AbstractUser user) {
        // 通过token获取用户的详细基本信息
        UserVO userVO = new UserVO(user);
        userVO.setAllFriendsCount(0);
        return new JsonObject<>(userVO);
    }
}
