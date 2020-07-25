package com.fb.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fb.common.util.LocalDateConvert;
import com.fb.common.util.LocalDateJsonDeserialize;
import com.fb.common.util.LocalDateTimeConvert;
import com.fb.common.util.LocalDateTimeJsonDeserialize;
import com.fb.user.enums.SexEnum;
import com.fb.user.enums.UserTypeEnum;
import com.fb.user.repository.HobbyTagPO;
import com.fb.user.repository.UserPO;
import com.fb.user.request.UserReq;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.convert.ThreeTenBackPortConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public abstract class AbstractUser {

    public AbstractUser() {}


    public AbstractUser(UserPO userPO) {
        this.uid = userPO.getId();
        this.name = userPO.getName();
        this.phoneNumber = userPO.getPhoneNumber();
        this.lat = userPO.getLat();
        this.lng = userPO.getLng();
        this.cityCode = userPO.getCityCode();
        this.adCode = userPO.getAdCode();
        this.cityName = userPO.getCityName();
        this.province = userPO.getProvince();
        this.adName = userPO.getAdName();
        this.birthday = userPO.getBirthday();
        this.sex = SexEnum.getSexEnumByCode(userPO.getSex());
        this.introduction = userPO.getIntroduction();
        this.hobbyTagList = Arrays.asList(StringUtils.split(userPO.getHobbyTagNameList(), ","));
    }

    //用户id
    protected Long uid;

    protected UserTypeEnum userTypeEnum;

    protected String name;

    protected String phoneNumber;

    protected BigDecimal lat;

    protected BigDecimal lng;

    protected String cityCode;

    protected String adCode;

    protected String province;

    protected String cityName;

    protected String adName;

    protected String locationStr;

    @JsonSerialize(using = LocalDateConvert.class)
    @JsonDeserialize(using = LocalDateJsonDeserialize.class)
    protected LocalDate birthday;

    protected SexEnum sex;

    protected String introduction;

    protected String headPicUrl;

    protected List<String> hobbyTagList;

    @JsonSerialize(using = LocalDateTimeConvert.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserialize.class)
    protected LocalDateTime createTime;

    private String loginToken;

    /**
     *判断用户是否入驻用户
     * @return
     */
    public boolean isMerchant() {
        return this instanceof MerchantUser;
    }

    public abstract UserPO convert2PO();

    public Long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

}
