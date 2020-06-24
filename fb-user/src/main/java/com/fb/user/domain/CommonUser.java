package com.fb.user.domain;

import com.fb.user.enums.UserTypeEnum;
import com.fb.user.repository.UserPO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 普通用户
 */
public class CommonUser extends AbstractUser {

    public CommonUser() {}

    public CommonUser(String name, String phoneNumber,
                      BigDecimal lat, BigDecimal lng, Integer cityCode, Integer adCode,
                      LocalDate birthday, Byte sex, String introduction) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.lat = lat;
        this.lng = lng;
        this.cityCode = cityCode;
        this.adCode = adCode;
        this.birthday = birthday;
        this.sex = sex;
        this.introduction = introduction;
    }

    @Override
    public AbstractUser convertByPO(UserPO userPO) {
        return null;
    }

    @Override
    public UserPO convert2PO() {
        UserPO userPO = new UserPO();
        userPO.setName(this.name);
        userPO.setAdCode(this.adCode);
        userPO.setBirthday(this.birthday);
        userPO.setCityCode(this.cityCode);
        userPO.setLat(this.lat);
        userPO.setLng(this.lng);
        userPO.setHeadPicUrl(this.headPicUrl);
        userPO.setIntroduction(this.introduction);
        userPO.setLocationStr(this.locationStr);
        userPO.setSex(this.sex);
        userPO.setPhoneNumber(this.phoneNumber);
        userPO.setUserType(UserTypeEnum.COMMON_USER.getCode());
        userPO.setCreateTime(LocalDateTime.now());
        return userPO;
    }

    //升级入驻用户：
}
