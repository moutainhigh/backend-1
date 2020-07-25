package com.fb.web.entity;

import com.fb.user.domain.AbstractUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-07-05 20:16
 */
@Data
@Api("用户信息")
public class BasicUserVO {


        public BasicUserVO() {};
        public BasicUserVO(AbstractUser abstractUser) {
            this.name = abstractUser.getName();
            this.phoneNumber = abstractUser.getPhoneNumber();
            this.lat = abstractUser.getLat();
            this.lng = abstractUser.getLng();
            this.birthdayTimeStamp = abstractUser.getBirthday().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            this.sexDesc = abstractUser.getSex().getDesc();
            this.introduction = abstractUser.getIntroduction();
            this.headPicUrl = abstractUser.getHeadPicUrl();
            this.hobbyTagNameList = abstractUser.getHobbyTagList();
            this.token = abstractUser.getLoginToken();
            this.userType = abstractUser.getUserTypeEnum().getCode();
            this.userTypeDesc = abstractUser.getUserTypeEnum().getDesc();
            this.sex = abstractUser.getSex().getCode();
            this.province = abstractUser.getProvince();
            this.adName = abstractUser.getAdName();
            this.cityName = abstractUser.getCityName();
        }

        @ApiModelProperty(value = "用户名称")
        private String name;

        @ApiModelProperty(value = "用户手机号")
        private String phoneNumber;

        @ApiModelProperty(value = "纬度")
        private BigDecimal lat;

        @ApiModelProperty(value = "经度")
        private BigDecimal lng;

        @ApiModelProperty("省")
        private String province;

        @ApiModelProperty("市")
        private String cityName;

        @ApiModelProperty("区县")
        private String adName;

        @ApiModelProperty(value = "用户类型 0：普通用户；1：入驻用户")
        private Byte userType;

        @ApiModelProperty(value = "用户类型描述")
        private String userTypeDesc;

        @ApiModelProperty(value = "生日时间戳，单位：ms")
        private Long birthdayTimeStamp;

        @ApiModelProperty(value = "用户性别描述")
        private String sexDesc;

        @ApiModelProperty(value = "用户性别，1：女，2：男")
        private Byte sex;

        @ApiModelProperty(value = "用户简介")
        private String introduction;

        @ApiModelProperty(value = "用户头像地址")
        private String headPicUrl;

        @ApiModelProperty(value = "用户爱好标签")
        private List<String> hobbyTagNameList;

        @ApiModelProperty(value = "用户登录成功的token值")
        private String token;

        @ApiModelProperty(value = "关系网人数")
        private int allFriendsCount;
}
