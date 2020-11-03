package com.fb.user.repository;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.omg.CORBA.IDLType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class UserPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("lat")
    private BigDecimal lat;

    @TableField("lng")
    private BigDecimal lng;

    @TableField("city_code")
    private String cityCode;

    @TableField("ad_code")
    private  String adCode;

    @TableField("province")
    private String province;

    @TableField("city_name")
    private String cityName;

    @TableField("ad_name")
    private String adName;

    @TableField("location_str")
    private String locationStr;

    @TableField("birthday")
    private LocalDate birthday;

    @TableField("sex")
    private Byte sex;

    @TableField("introduction")
    private String introduction;

    @TableField("head_pic_url")
    private String headPicUrl;

    @TableField("user_type")
    private Byte userType;

    @TableField("hobby_tag_list")
    private String hobbyTagNameList;

    @TableField("create_time")
    private LocalDateTime createTime;
}
