package com.fb.web.entity.output;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "评论模型")
public class CommentDetailVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "信息id")
    private Long infoId;

    @ApiModelProperty(value = "类型", allowableValues = "1 活动, 2 动态")
    private Integer infoType;

    @ApiModelProperty(value = "评论用户")
    private Long userId;

    @ApiModelProperty(value = "评论用户昵称")
    private String nickname;

    @ApiModelProperty(value = "被评论用户")
    private Long toUserId;

    @ApiModelProperty(value = "被评论用户昵称")
    private String toNickname;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论时间")
    private Long commentTime;
}
