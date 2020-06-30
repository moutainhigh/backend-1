package com.fb.web.entity.output;

import com.fb.web.entity.CommonVO;
import com.fb.web.entity.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Api(value = "动态模型")
public class FeedDetailVO extends CommonVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户信息")
    private UserVO userVO;

    @ApiModelProperty(value = "发布时间")
    private Long publishTime;

    @ApiModelProperty(value = "发布地点")
    private String cityName;

    @ApiModelProperty(value = "内容", notes = "长度100字")
    private String content;

    @ApiModelProperty(value = "图片url", notes = "多个用,分割（图片6张）")
    private String picUrl;

    @ApiModelProperty(value = "视频url", notes = "多个用,分割（1视频）")
    private String videoUrl;

    @ApiModelProperty(value = "打卡", required = true, allowableValues = "0 否 1 是")
    private Integer clockIn;

    @ApiModelProperty(value = "打卡标签")
    private String clockInTag;

    @ApiModelProperty(value = "点赞")
    private int likeNum;

    @ApiModelProperty(value = "已评论")
    private int commentNum;

    @ApiModelProperty(value = "当前用户是否点赞")
    private boolean like;
}
