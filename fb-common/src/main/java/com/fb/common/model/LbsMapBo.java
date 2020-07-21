package com.fb.common.model;

import lombok.Data;

@Data
public class LbsMapBo {
    /*省中文*/
    private String province;
    /*城市编码*/
    private String cityCode;
    /*行政区编码*/
    private String adCode;
    /*城市名字*/
    private String cityName;
    /*行政区名字*/
    private String adName;


}
