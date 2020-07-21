package com.fb.user.domain;

import com.fb.user.enums.SexEnum;
import com.fb.user.enums.UserTypeEnum;
import com.fb.user.repository.UserPO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 普通用户
 */
public class CommonUser extends AbstractUser {

    public CommonUser() {}

    public CommonUser(UserPO userPO) {
      super(userPO);
      this.userTypeEnum = UserTypeEnum.COMMON_USER;
    }

    public CommonUser(String name, String phoneNumber,
                      BigDecimal lat, BigDecimal lng, Integer cityCode, Integer adCode,
                      LocalDate birthday, byte sex, String introduction) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.lat = lat;
        this.lng = lng;
        this.cityCode = cityCode;
        this.adCode = adCode;
        this.birthday = birthday;
        this.sex = SexEnum.getSexEnumByCode(sex);
        this.introduction = introduction;
    }


    @Override
    public UserPO convert2PO() {
        UserPO userPO = new UserPO();
        userPO.setId(this.uid);
        userPO.setName(this.name);
        userPO.setAdCode(this.adCode);
        userPO.setBirthday(this.birthday);
        userPO.setCityCode(this.cityCode);
        userPO.setLat(this.lat);
        userPO.setLng(this.lng);
        userPO.setHeadPicUrl(this.headPicUrl);
        userPO.setIntroduction(this.introduction);
        userPO.setLocationStr(this.locationStr);
        userPO.setSex(this.sex.getCode());
        userPO.setPhoneNumber(this.phoneNumber);
        userPO.setUserType(UserTypeEnum.COMMON_USER.getCode());
        userPO.setCreateTime(LocalDateTime.now());
        if (CollectionUtils.isNotEmpty(this.hobbyTagList)) {
            userPO.setHobbyTagNameList(StringUtils.join(this.hobbyTagList, ","));
        }
        return userPO;
    }

    //升级入驻用户：
}
