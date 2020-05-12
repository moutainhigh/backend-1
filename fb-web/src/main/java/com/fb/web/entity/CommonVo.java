package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonVo {
    @ApiModelProperty(value = "经纬度坐标")
    private String location;
}
