package com.fb.web.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "活动模型")
public class IdVO {

    @NotNull(message = "id is not null")
    @ApiModelProperty(value = "id", required = true)
    private Long id;
}
