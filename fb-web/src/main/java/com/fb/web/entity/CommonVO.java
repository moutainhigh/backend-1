package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommonVO {
//    @NotNull(message = "location is not null")
    @ApiModelProperty(value = "经纬度坐标", required = true)
    private String location;
}
