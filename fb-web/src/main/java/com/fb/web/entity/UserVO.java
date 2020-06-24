package com.fb.web.entity;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "用户模型")
public class UserVO {
    @ApiModelProperty(value = "uid")
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户头像")
    private String pic;
    @ApiModelProperty(value = "用户关系")
    private int order;
}
