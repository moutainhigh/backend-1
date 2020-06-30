package com.fb.web.controller;

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

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
@Api(value = "用户", description = "用户相关接口")
@ResponseBody
@Slf4j
public class UserController {

    @Autowired
    private FeedFacadeService feedFacadeService;

    @Autowired
    private ActivityFacadeService activityFacadeService;


    //    TODO LX 二期
    @ApiOperation(value = "人脉关系(二期)", notes = "查询人脉关系，3级")
    @RequestMapping(value =
            "/user/relation", method = {RequestMethod.GET})
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

        Optional<List<ActivityDetailVO>> activityListVOList = activityFacadeService.queryActivityListFollow(userIdList, limit, activityOffsetId);

        Optional<List<FeedDetailVO>> feedDetailVOList = feedFacadeService.queryActivityListFollow(userIdList, limit, feedOffsetId, userId);

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
            if (length == limit) {
                hasNext = true;
            }
            for (int i = 0; i < relationDetails.size(); i++) {
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
