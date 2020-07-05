package com.fb.web.entity.output;

import com.fb.web.entity.CommonVO;
import com.fb.web.entity.TicketVO;
import com.fb.web.entity.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Api(value = "活动详情")
public class ActivityDetailVO extends CommonVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户信息")
    private UserVO userVO;

    @ApiModelProperty(value = "发布时间，格式yyyy-MM-dd HH:mm")
    private String publishTime;

    @ApiModelProperty(value = "发布地点")
    private String cityName;

    @ApiModelProperty(value = "活动时间，格式yyyy-MM-dd HH:mm")
    private String activityTime;

    @ApiModelProperty(value = "发布人类型")
    private Integer userType;

    @ApiModelProperty(value = "定金，只有用户才有")
    private BigDecimal frontMoney;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "活动人数")
    private Integer memberCount;

    @ApiModelProperty(value = "有效期", allowableValues = "0 长期 1 短期")
    private Integer activityValid;

    @ApiModelProperty(value = "报名结束时间，格式yyyy-MM-dd HH:mm")
    private String enrollEndTime;

    @ApiModelProperty(value = "活动地址")
    private String activityAddress;

    @ApiModelProperty(value = "活动类型")
    private Integer activityType;

    @ApiModelProperty(value = "活动类型中文")
    private String activityTypeName;

    @ApiModelProperty(value = "需要购票信息", required = true, allowableValues = "0 否 1 是")
    private int needInfo;

    @ApiModelProperty(value = "退票设置", required = true, allowableValues = "0 不可退, 1 可退")
    private int refundFlag;

    @ApiModelProperty(value = "图片url", notes = "多个用,分割（图片9张）")
    private String picUrl;

    @ApiModelProperty(value = "视频url", notes = "多个用,分割（1视频）")
    private String videoUrl;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "设置票种")
    private List<TicketVO> ticketVoList;


    @ApiModelProperty(value = "参加活动前群聊")
    private Long groupId;

    @ApiModelProperty(value = "参加活动后群聊")
    private Long payGroupId;

}

