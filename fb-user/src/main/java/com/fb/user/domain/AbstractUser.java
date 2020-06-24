package com.fb.user.domain;

import com.fb.user.repository.UserPO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractUser {

    //用户id
    private Long uid;

    protected String name;

    protected String phoneNumber;

    protected BigDecimal lat;

    protected BigDecimal lng;

    protected Integer cityCode;

    protected Integer adCode;

    protected String locationStr;

    protected LocalDate birthday;

    protected Byte sex;

    protected String introduction;

    protected String headPicUrl;

    private List<Integer> hobbyTagIdList;

    protected LocalDateTime createTime;

    private String loginToken;




    /**
     *判断用户是否入驻用户
     * @return
     */
    public boolean isMerchant() {
        return this instanceof MerchantUser;
    }

    public abstract AbstractUser convertByPO(UserPO userPO);

    public abstract UserPO convert2PO();

    public Long getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
