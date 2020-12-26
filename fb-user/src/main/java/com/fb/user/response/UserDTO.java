package com.fb.user.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fb.common.util.LocalDateConvert;
import com.fb.common.util.LocalDateJsonDeserialize;
import com.fb.common.util.LocalDateTimeConvert;
import com.fb.common.util.LocalDateTimeJsonDeserialize;
import com.fb.user.enums.SexEnum;
import com.fb.user.enums.UserTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-10-26 22:54
 */
@Data
public class UserDTO {
    private Long uid;

    private UserTypeEnum userTypeEnum;

    private String name;

    private String phoneNumber;

    private BigDecimal lat;

    private BigDecimal lng;

    private String cityCode;

    private String adCode;

    private String province;

    private String cityName;

    private String adName;

    private String locationStr;

    @JsonSerialize(using = LocalDateConvert.class)
    @JsonDeserialize(using = LocalDateJsonDeserialize.class)
    private LocalDate birthday;

    private SexEnum sex;

    private String introduction;

    private String headPicUrl;

    private List<String> hobbyTagList;

    @JsonSerialize(using = LocalDateTimeConvert.class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserialize.class)
    private LocalDateTime createTime;

    private String loginToken;

    private IMAccount imAccount;
}
