package com.fb.web.entity;

import com.fb.user.domain.AbstractUser;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-07-05 20:16
 */
@Data
public class BasicUserVO {


        public BasicUserVO() {};
        public BasicUserVO(AbstractUser abstractUser) {
            this.name = abstractUser.getName();
            this.phoneNumber = abstractUser.getPhoneNumber();
            this.lat = abstractUser.getLat();
            this.lng = abstractUser.getLng();
            this.cityCode = abstractUser.getCityCode();
            this.adCode = abstractUser.getAdCode();
            this.locationStr = abstractUser.getLocationStr();
            this.birthdayTimestamp = abstractUser.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            this.sexDesc = abstractUser.getSex().getDesc();
            this.introduction = abstractUser.getIntroduction();
            this.headPicUrl = abstractUser.getHeadPicUrl();
            this.hobbyTagNameList = abstractUser.getHobbyTagList();
        }

        private String name;

        private String phoneNumber;

        private BigDecimal lat;

        private BigDecimal lng;

        private Integer cityCode;

        private Integer adCode;

        private String locationStr;

        private Long birthdayTimestamp;

        private String sexDesc;

        private String introduction;

        private String headPicUrl;

        private List<String> hobbyTagNameList;

        private int allFriendsCount;
}
