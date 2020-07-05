package com.fb.user.request;

import com.fb.user.repository.HobbyTagPO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 11:52
 */
@Data
public class UserReq {

    private Long uid;

    private String name;

    private String phoneNumber;

    private BigDecimal lat;

    private BigDecimal lng;

    private Integer cityCode;

    private Integer adCode;

    private String locationStr;

    private String birthday;

    private byte sex;

    private String introduction;

    private String headPicUrl;

    private String loginToken;

    private List<String> hobbyTagNameList;
}
