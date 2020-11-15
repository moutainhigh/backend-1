package com.fb.web.controller;

import com.fb.addition.enums.InfoTypeEnum;
import com.fb.common.util.CmsUtils;
import com.fb.common.util.DateUtils;
import com.fb.common.util.RedisUtils;
import com.fb.user.repository.HobbyTagPO;
import com.fb.user.request.UserReq;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IHobbyTagService;
import com.fb.user.service.IUserService;
import com.fb.web.entity.BasicUserVO;
import com.fb.web.entity.UserVO;
import com.fb.web.entity.output.ActivityDetailVO;
import com.fb.web.entity.output.FeedDetailVO;
import com.fb.web.entity.output.FocusVO;
import com.fb.web.entity.output.RelationDetail;
import com.fb.web.exception.UserResponse;
import com.fb.web.exception.valid.Create;
import com.fb.web.exception.valid.Modify;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.service.FeedFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fb.common.util.DateUtils.zoneId;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
@Api(value = "用户", description = "用户相关接口")
@Slf4j
@Validated
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IHobbyTagService hobbyTagService;
    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;

    @Autowired
    private FeedFacadeService feedFacadeService;

    @Autowired
    private ActivityFacadeService activityFacadeService;




    //请求验证码接口, todo 验证手机号是否有效
    @GetMapping("/getVerifyCode/{phoneNumber}")
    @ApiOperation(value = "获取手机验证码")
    public JsonObject getVerifyCode(@NotBlank @ApiParam(name = "phoneNumber", value = "手机号", required = true)
                                        @PathVariable String phoneNumber) {

        //FIXME
        //        if (CmsUtils.sendVerifyCode(redisUtils, phoneNumber)){

            return new JsonObject(CmsUtils.sendVerifyCodeTest(redisUtils, phoneNumber));
//        }
//        return JsonObject.newErrorJsonObject(UserResponse.SEND_VERIFYCODE_ERROR);
    }

    //验证码 + 手机号进行验证：登录成功，返回token值；用户未注册，前端进入注册页面
    @GetMapping("/login")
    @ApiOperation(value = "用户登录", notes = "0:登录成功；10002：验证码失败；10001：手机号不存在，请注册")
    public JsonObject<BasicUserVO> verify(@NotBlank @ApiParam(name = "phoneNumber", value = "手机号", required = true)  @RequestParam("phoneNumber") String phoneNumber,
                                          @NotBlank @ApiParam(name = "verifyCode", value = "验证码", required = true) @RequestParam("verifyCode") String verifyCode) {
        //todo 手机验证码的校验
        boolean checkResult = CmsUtils.checkVerifyCode(redisUtils, phoneNumber, verifyCode);
        if (checkResult) {
            UserDTO user = userService.login(phoneNumber);
            if (Objects.isNull(user)) {
                //返回不存在该用户，直接注册用户
                return JsonObject.newErrorJsonObject(UserResponse.USER_NOT_EXIST);
            }
            BasicUserVO userVO = new BasicUserVO(user);
            return new JsonObject<>(userVO);
        }
        return JsonObject.newErrorJsonObject(UserResponse.VERIFY_FAIL);
    }

    //注册，注册之后也应该把详细信息返回到设备端
    @PostMapping("/signUp")
    @ApiOperation("新用户注册")
    public JsonObject<BasicUserVO> signUp(@RequestBody @Validated({Create.class}) UserReq userReq) {
        UserDTO user = userService.createUser(userReq);
        BasicUserVO userVO = new BasicUserVO(user);
        return new JsonObject<>(userVO);
    }

    @GetMapping("/listHobbyTagName")
    @ApiOperation("获取所有爱好标签")
    public JsonObject<List<String>> listHobbyTag() {
        return new JsonObject<>(hobbyTagService.listAllHobbyTag().stream().map(HobbyTagPO::getName).collect(Collectors.toList()));
    }


    @PutMapping("/modifyUser")
    @ApiOperation("修改用户信息")
    public JsonObject<BasicUserVO> modifyUser(@RequestBody @Validated({Modify.class}) UserReq userReq,
                                              @ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser, @RequestHeader("token") String token) {
        userReq.setUid(sessionUser.getUid());
        userReq.setLoginToken(token);
        UserDTO user = userService.modifyUser(userReq);
        return new JsonObject<>(new BasicUserVO(user));
    }

    @PutMapping("")
    @ApiModelProperty("普通用户申请入驻接口")
    // 普通用户申请入驻，并且返回入驻用户
    public JsonObject<BasicUserVO> promote() {
        return null;
    }


    //用户的基本信息获取
    @GetMapping("/detail")
    @ApiOperation("通过token获取用户详细信息")
    @ApiImplicitParam(name = "token", value = "token",required = true, dataType = "String",paramType="header")
    public JsonObject<BasicUserVO> getBasicInfoByToken(@RequestAttribute(name = "user") @ApiIgnore UserDTO user) {
        // 通过token获取用户的详细基本信息
        BasicUserVO userVO = new BasicUserVO(user);
        userVO.setAllFriendsCount(0);
        return new JsonObject<>(userVO);
    }


    @ApiOperation(value = "人脉关系(二期)", notes = "查询人脉关系，3级")
    @RequestMapping(value =
            "/relation", method = {RequestMethod.GET})
    public JsonObject<List<UserVO>> getUserRelation() {
        return JsonObject.newCorrectJsonObject("");
    }


    @ApiOperation(value = "关系网(二期)", notes = "")
    @RequestMapping(value = "/relationfeed", method = {RequestMethod.GET})
    public JsonObject<FocusVO> getUserFocusList(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser,
                                                @ApiParam(name = "limit", value = "展示条数") @RequestParam("limit") Integer limit,
                                                @ApiParam(name = "feedOffsetId", value = "动态偏移量 ") @RequestParam("feedOffsetId") Long feedOffsetId,
                                                @ApiParam(name = "activityOffsetId", value = "活动偏移量 ") @RequestParam("activityOffsetId") Long activityOffsetId) {

        if (Objects.isNull(feedOffsetId)) {
            feedOffsetId = 0L;
        }
        if (Objects.isNull(activityOffsetId)) {
            activityOffsetId = 0L;
        }
        if (Objects.isNull(limit) || limit > 20) {
            limit = 10;
        }

        Long userId = sessionUser.getUid();
        //TODO LX 查好友
        List<Long> userIdList = null;
        FocusVO focusVO = new FocusVO();
        List<RelationDetail> relationDetails = new ArrayList<>(2 * limit);
        List<RelationDetail> relationDetailResult = new ArrayList<>(limit);

        Optional<List<ActivityDetailVO>> activityListVOList = activityFacadeService.queryActivityListFollow(userIdList, limit, activityOffsetId, userId);

        Optional<List<FeedDetailVO>> feedDetailVOList = feedFacadeService.queryFeedListFollow(userIdList, limit, feedOffsetId, userId);

        if (activityListVOList.isPresent()) {
            activityListVOList.get().forEach(activityDetailVO -> {

                RelationDetail detail = new RelationDetail();
                detail.setActivityDetailVO(activityDetailVO);
                detail.setTime(LocalDate.parse(activityDetailVO.getPublishTime(), DateUtils.dateTimeFormatterMin));
                detail.setInfoType(InfoTypeEnum.ACTIVITY.getCode());
                relationDetails.add(detail);
            });
        }

        if (feedDetailVOList.isPresent()) {
            feedDetailVOList.get().forEach(feedDetailVO -> {
                RelationDetail detail = new RelationDetail();
                detail.setFeedDetailVO(feedDetailVO);
                detail.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(feedDetailVO.getPublishTime()), zoneId).toLocalDate());
                detail.setInfoType(InfoTypeEnum.FEED.getCode());
                relationDetails.add(detail);
            });
        }
        boolean hasNext = false;
        //日期降序
        if (!CollectionUtils.isEmpty(relationDetails)) {
            relationDetails.sort((a, b) -> b.getTime().compareTo(a.getTime()));
            //遍历找到offset
            if (relationDetails.size() > limit) {
                hasNext = true;
            }
            int length = Math.min(relationDetails.size(), limit);

            for (int i = 0; i < length; i++) {
                RelationDetail relationDetail = relationDetails.get(i);
                relationDetailResult.add(relationDetail);
                if (relationDetail.getInfoType() == InfoTypeEnum.ACTIVITY.getCode()) {
                    activityOffsetId = relationDetail.getActivityDetailVO().getId();
                }
                if (relationDetail.getInfoType() == InfoTypeEnum.FEED.getCode()) {
                    feedOffsetId = relationDetail.getFeedDetailVO().getId();
                }
            }
            focusVO.setActivityOffsetId(activityOffsetId);
            focusVO.setFeedOffsetId(feedOffsetId);
            focusVO.setRelationDetailList(relationDetailResult);
        }
        focusVO.setHasNext(hasNext);

        return JsonObject.newCorrectJsonObject(focusVO);
    }
}
