package com.fb.user.service.impl;

import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import com.fb.common.util.RedisUtils;
import com.fb.user.enums.SexEnum;
import com.fb.user.enums.UserTypeEnum;
import com.fb.user.repository.HobbyTagRepository;
import com.fb.user.repository.UserPO;
import com.fb.user.repository.UserRepository;
import com.fb.user.request.UserReq;
import com.fb.user.response.UserDTO;
import com.fb.user.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.*;

@Service
public class UserServiceImpl implements IUserService {

    private final static int TOKEN_TIMEOUT = 3; //3天的过期时间


    public String string;

    @Resource
    @Qualifier("userRedis")
    private RedisUtils redisUtils;
    @Resource
    private UserRepository userRepository;
    @Resource
    private HobbyTagRepository hobbyTagRepository;
    @Resource
    private LbsMapService lbsMapService;


    @Override
    public UserDTO checkAndRefresh(String token) {
        UserDTO user = (UserDTO) redisUtils.getCacheObject(token);
        if (Objects.isNull(user)) return null;
        redisUtils.expire(token, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return user;
    }

    @Override
    public String getUserNameByToken(String token) {
        UserDTO user = (UserDTO) redisUtils.getCacheObject(token);
        return user.getName();
    }

    @Override
    public UserDTO createUser(UserReq userReq) {
        UserPO userPO = UserReq2UserPO(userReq);
        userPO.setCreateTime(LocalDateTime.now());
        userPO.setUserType(UserTypeEnum.COMMON_USER.getCode());
        UserPO result =  userRepository.save(userPO);
        handleHobbyTag(userReq.getHobbyTagNameList());
        UserDTO userDTO = userPO2UserDTO(result);
        String token = result.getId().toString() + "_" +  System.currentTimeMillis();
        userDTO.setLoginToken(token);
        redisUtils.setCacheObject(token, userDTO, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return userDTO;
    }

    @Override
    public UserDTO login(String phoneNumber) {
        UserPO userPO = userRepository.getOneUserByPhoneNumber(phoneNumber);
        if (Objects.isNull(userPO))
            return null;
        String token = userPO.getId().toString() +  "_" + System.currentTimeMillis();
        UserDTO userDTO = userPO2UserDTO(userPO);
        userDTO.setLoginToken(token);
        redisUtils.setCacheObject(token, userDTO, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return userDTO;
    }

    private void handleHobbyTag(List<String> hobbyTagList) {
        if (CollectionUtils.isNotEmpty(hobbyTagList)) {
            hobbyTagList = hobbyTagList.stream().distinct().collect(Collectors.toList());
            hobbyTagRepository.insertIgnore(hobbyTagList);
        }
    }

    @Override
    public UserDTO modifyUser(UserReq userReq) {
        UserPO lastUser = userRepository.getOneUserById(userReq.getUid());
        if (Objects.isNull(lastUser))
            throw new RuntimeException("未发现该用户");
        //解析userReq，修改的req肯定是有不被修改的传递为null值。
        UserPO userPO = UserReq2UserPO(userReq);
        userPO.setId(lastUser.getId());
        userRepository.save(userPO);
        UserDTO userDTO = modifyUserPO2UserDTO(lastUser, userPO);
        redisUtils.setCacheObject(userReq.getLoginToken(), userDTO, TOKEN_TIMEOUT, TimeUnit.DAYS);
        return userDTO;
    }

    private UserDTO modifyUserPO2UserDTO(UserPO lastUser, UserPO newUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(lastUser.getId());
        userDTO.setName(Objects.nonNull(newUser.getName()) ? newUser.getName() : lastUser.getName());
        userDTO.setPhoneNumber(lastUser.getPhoneNumber());
        userDTO.setLat(Objects.nonNull(newUser.getLat()) ? newUser.getLat() : lastUser.getLat());
        userDTO.setLng(Objects.nonNull(newUser.getLat()) ? newUser.getLat() : lastUser.getLat());
        userDTO.setCityCode(Objects.nonNull(newUser.getCityCode()) ? newUser.getCityCode() : lastUser.getCityCode());
        userDTO.setAdCode(Objects.nonNull(newUser.getAdCode()) ? newUser.getAdCode() : lastUser.getAdCode());
        userDTO.setCityName(Objects.nonNull(newUser.getCityName()) ? newUser.getCityName() : lastUser.getCityName());
        userDTO.setProvince(Objects.nonNull(newUser.getProvince()) ? newUser.getProvince() : lastUser.getProvince());
        userDTO.setAdName(Objects.nonNull(newUser.getAdName()) ? newUser.getAdName() : lastUser.getAdName());
        userDTO.setBirthday(Objects.nonNull(newUser.getBirthday()) ? newUser.getBirthday() : lastUser.getBirthday());
        userDTO.setSex(Objects.nonNull(newUser.getSex()) ? SexEnum.getSexEnumByCode(newUser.getSex()) : SexEnum.getSexEnumByCode(lastUser.getSex()));
        userDTO.setIntroduction(Objects.nonNull(newUser.getIntroduction()) ? newUser.getIntroduction() : lastUser.getIntroduction());
        userDTO.setHeadPicUrl(Objects.nonNull(newUser.getHeadPicUrl()) ? newUser.getHeadPicUrl() : lastUser.getHeadPicUrl());
        userDTO.setHobbyTagList(Objects.nonNull(newUser.getHobbyTagNameList())
                ? Arrays.asList(StringUtils.split(newUser.getHobbyTagNameList(), ",")) : Arrays.asList(StringUtils.split(lastUser.getHobbyTagNameList(), ",")));
        userDTO.setUserTypeEnum(UserTypeEnum.getUserTypeEnumByCode(lastUser.getUserType()));
        return userDTO;
    }

    /**
     * 创建和修改用户基本信息req转换为po，为null的就不会对数据库发生修改
     * @param userReq
     * @return
     */
    private UserPO UserReq2UserPO(UserReq userReq) {
        UserPO userPO = new UserPO();
        if (Objects.nonNull(userReq.getLat()) && Objects.nonNull(userReq.getLng())) {
            Optional<LbsMapBo> optional = lbsMapService.getLbsInfoByLonAndLat(userReq.getLng().toPlainString(),
                    userReq.getLat().toPlainString());

            if (!optional.isPresent())
                throw new RuntimeException("获取具体地址出错");
            LbsMapBo lbsMapBo = optional.get();
            userPO.setAdCode(lbsMapBo.getAdCode());
            userPO.setAdName(lbsMapBo.getAdName());
            userPO.setProvince(lbsMapBo.getProvince());
            userPO.setCityName(lbsMapBo.getCityName());
            userPO.setCityCode(lbsMapBo.getCityCode());
        }
        userPO.setBirthday(LocalDate.parse(userReq.getBirthday(), ofPattern("yyyy-MM-dd")));
        userPO.setHeadPicUrl(userReq.getHeadPicUrl());
        if (CollectionUtils.isNotEmpty(userReq.getHobbyTagNameList())) {
            userPO.setHobbyTagNameList(StringUtils.join(userReq.getHobbyTagNameList(), ","));
        }
        userPO.setIntroduction(userReq.getIntroduction());
        userPO.setLat(userReq.getLat());
        userPO.setLng(userReq.getLng());
        userPO.setLocationStr(userReq.getLocationStr());
        userPO.setName(userReq.getName());
        userPO.setSex(userReq.getSex());
        userPO.setPhoneNumber(userReq.getPhoneNumber());
        return userPO;
    }

    private UserDTO userPO2UserDTO(UserPO userPO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(userPO.getId());
        userDTO.setName(userPO.getName());
        userDTO.setPhoneNumber(userPO.getPhoneNumber());
        userDTO.setLat(userPO.getLat());
        userDTO.setLng(userPO.getLng());
        userDTO.setCityCode(userPO.getCityCode());
        userDTO.setAdCode(userPO.getAdCode());
        userDTO.setCityName(userPO.getCityName());
        userDTO.setProvince(userPO.getProvince());
        userDTO.setAdName(userPO.getAdName());
        userDTO.setBirthday(userPO.getBirthday());
        userDTO.setSex(SexEnum.getSexEnumByCode(userPO.getSex()));
        userDTO.setIntroduction(userPO.getIntroduction());
        userDTO.setHeadPicUrl(userPO.getHeadPicUrl());
        userDTO.setHobbyTagList(StringUtils.isBlank(userPO.getHobbyTagNameList()) ? null : Arrays.asList(StringUtils.split(userPO.getHobbyTagNameList(), ",")));
        userDTO.setUserTypeEnum(UserTypeEnum.getUserTypeEnumByCode(userPO.getUserType()));
        return userDTO;
    }

    @Override
    public UserDTO getUserByUid(Long uid) {
        UserPO userPO;
        if (Objects.nonNull(userPO = userRepository.getOneUserById(uid))) return userPO2UserDTO(userPO);
        return null;
    }

    @Override
    public List<UserDTO> listSimpleUser(List<Long> uidList) {
        return userRepository.listSimpleUserByUidList(uidList).stream().map(this::userPO2UserDTO).collect(Collectors.toList());
    }
}
