package com.fb.web.controller;

import com.fb.relation.service.DTO.UserDTOForRelation;
import com.fb.relation.service.IUserRelationService;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IUserService;
import com.fb.web.entity.BasicUserVO;
import com.fb.web.exception.UserResponse;
import com.fb.web.utils.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 10:50
 */
@RestController
@RequestMapping(value = "user_relation", produces = "application/json;charset=UTF-8")
@Api(value = "用户关系", description = "用户关系相关接口")
public class UserRelationController {


    @Resource
    private IUserRelationService userRelationService;
    @Resource
    private IUserService userService;

    //通过关系id获取关系属性

    @GetMapping("/addUser/{targetUid}")
    public JsonObject addUser(@NotBlank @ApiParam(name = "targetUid", value = "目标用户id", required = true)
                              @PathVariable Long targetUid,
                              @ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser) {
        UserDTO userDTO = userService.getUserByUid(targetUid);
        if (Objects.isNull(userDTO)) return JsonObject.newErrorJsonObject(UserResponse.USER_NOT_EXIST);
        UserDTOForRelation user1 = new UserDTOForRelation(userDTO.getUid(), userDTO.getCityCode());
        UserDTOForRelation user2 = new UserDTOForRelation(sessionUser.getUid(), sessionUser.getCityCode());
        userRelationService.addFriend(user1, user2);
        return new JsonObject();
    }

    @GetMapping("removeUser/{targetUid}")
    public JsonObject removeUser(@PathVariable Long targetUid, @ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser) {
        UserDTO userDTO = userService.getUserByUid(targetUid);
        if (Objects.isNull(userDTO)) return JsonObject.newErrorJsonObject(UserResponse.USER_NOT_EXIST);
        UserDTOForRelation user1 = new UserDTOForRelation(userDTO.getUid(), userDTO.getCityCode());
        UserDTOForRelation user2 = new UserDTOForRelation(sessionUser.getUid(), sessionUser.getCityCode());
        userRelationService.removeFriend(user1, user2);
        return new JsonObject();
    }

    @GetMapping("listDirect")
    public JsonObject<List<BasicUserVO>> listDirect(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser) {
        List<Long> directUserIdList = userRelationService.listDirectFriends(sessionUser.getUid());
        return new JsonObject<>(userService.listSimpleUser(directUserIdList).stream().map(BasicUserVO::new).collect(Collectors.toList()));
    }

    @GetMapping("listFans")
    public JsonObject<List<BasicUserVO>> listFans(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser) {
        List<Long> fansUserIdList = userRelationService.listFansUserId(sessionUser.getUid());
        return new JsonObject<>(userService.listSimpleUser(fansUserIdList).stream().map(BasicUserVO::new).collect(Collectors.toList()));
    }

    @GetMapping("listFollows")
    public JsonObject<List<BasicUserVO>> listFollows(@ApiIgnore @RequestAttribute(name = "user") UserDTO sessionUser) {
        List<Long> followUidList = userRelationService.listFollowUserId(sessionUser.getUid());
        return new JsonObject<>(userService.listSimpleUser(followUidList).stream().map(BasicUserVO::new).collect(Collectors.toList()));
    }

    @GetMapping("follow/{targetUid}")
    public JsonObject followUser(@PathVariable Long targetUid, @ApiIgnore @RequestAttribute("user") UserDTO sessionUser) {
        UserDTO userDTO = userService.getUserByUid(targetUid);
        if (Objects.isNull(userDTO)) return JsonObject.newErrorJsonObject(UserResponse.USER_NOT_EXIST);
        userRelationService.followUser(sessionUser.getUid(), targetUid);
        return new JsonObject();
    }

    @GetMapping("unFollow/{targetUid}")
    public JsonObject unFollowUser(@PathVariable Long targetUid, @ApiIgnore @RequestAttribute("user") UserDTO sessionUser
                                   ) {
        userRelationService.unFollowUser(sessionUser.getUid(), targetUid);
        return new JsonObject();
    }

}
