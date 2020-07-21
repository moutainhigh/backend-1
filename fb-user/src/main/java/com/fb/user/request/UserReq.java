package com.fb.user.request;

import com.fb.user.repository.HobbyTagPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 11:52
 */
@Data
@Api("用户请求体")
public class UserReq {


    private Long uid;

    @ApiModelProperty(value = "用户名称", required = true)
    private String name;

    @ApiModelProperty(value = "用户手机号", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "纬度,小数点后六位", required = true)
    private BigDecimal lat;

    @ApiModelProperty(value = "经度，小数点后六位", required = true)
    private BigDecimal lng;


    private Integer cityCode;

    private Integer adCode;

    private String locationStr;

    @ApiModelProperty(value = "用户生日, 格式yyyy-MM-dd")
    private String birthday;

    @ApiModelProperty(value = "用户性别 1：女性，2：男性", required = true)
    private byte sex;

    @ApiModelProperty(value = "用户简介")
    private String introduction;

    @ApiModelProperty(value = "用户头像地址", required = true)
    private String headPicUrl;

    private String loginToken;

    @ApiModelProperty
    private List<String> hobbyTagNameList;
}
