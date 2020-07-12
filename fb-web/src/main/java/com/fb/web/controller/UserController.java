package com.fb.web.controller;

import com.fb.common.util.CmsUtils;
import com.fb.common.util.RedisUtils;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.repository.HobbyTagPO;
import com.fb.user.request.UserReq;
import com.fb.user.service.IHobbyTagService;
import com.fb.user.service.IUserService;
import com.fb.web.entity.BasicUserVO;
import com.fb.web.entity.UserVO;
import com.fb.web.exception.UserResponse;
import com.fb.web.utils.JsonObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fb.addition.enums.InfoTypeEnum;
import com.fb.common.util.DateUtils;
import com.fb.web.entity.output.*;
import com.fb.web.entity.UserVO;
import com.fb.web.service.ActivityFacadeService;
import com.fb.web.service.FeedFacadeService;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fb.common.util.DateUtils.dateTimeFormatterMin;
import static com.fb.common.util.DateUtils.zoneId;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
@Api(value = "用户", description = "用户相关接口")
@ResponseBody
@Slf4j
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
    public JsonObject<BasicUserVO> verify(String phoneNumber, String verifyCode) {
        //todo 手机验证码的校验
        boolean checkResult = cmsUtils.checkVerifyCode(phoneNumber, verifyCode);
        if (checkResult) {
            AbstractUser user = userService.login(phoneNumber);
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
    public JsonObject<BasicUserVO> signUp(@RequestBody UserReq userReq) {
        CommonUser user = userService.createUser(userReq);
        BasicUserVO userVO = new BasicUserVO(user);
        return new JsonObject<>(userVO);
    }

    //获取爱好标签接口
    @GetMapping("/listHobbyTagName")
    public JsonObject<List<HobbyTagPO>> listHobbyTag() {
        return new JsonObject<>(hobbyTagService.listAllHobbyTag());
    }


    //修改用户基本信息接口，是否
    public JsonObject<BasicUserVO> modifyUser(@RequestBody UserReq userReq) {
        AbstractUser user = userService.modifyUser(userReq);
        return new JsonObject<>(new BasicUserVO(user));
    }


    //用户的基本信息获取
    public JsonObject<BasicUserVO> getBasicInfoByToken(@RequestAttribute(name = "user")AbstractUser user) {
        // 通过token获取用户的详细基本信息
        BasicUserVO userVO = new BasicUserVO(user);
        userVO.setAllFriendsCount(0);
        return new JsonObject<>(userVO);
    }


    @Autowired
    private FeedFacadeService feedFacadeService;

    @Autowired
    private ActivityFacadeService activityFacadeService;


    //    TODO LX 二期
    @ApiOperation(value = "人脉关系(二期)", notes = "查询人脉关系，3级")
    @RequestMapping(value ="/user/relation", method = {RequestMethod.GET})
    public JsonObject<List<UserVO>> getUserRelation() {
        return JsonObject.newCorrectJsonObject("");
    }


    //    TODO LX 二期
    @ApiOperation(value = "关系网(二期)", notes = "")
    @RequestMapping(value = "/user/relationfeed", method = {RequestMethod.GET})
    public JsonObject<FocusVO> getUserFocusList(@ApiParam(name = "limit", value = "展示条数") @RequestParam("limit") Integer limit,
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

        Long userId = 123456L;
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
            int length = Math.max(relationDetails.size(), limit);
            if (length > limit) {
                hasNext = true;
            }
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
