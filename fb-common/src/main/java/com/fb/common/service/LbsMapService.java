package com.fb.common.service;

import com.fb.common.model.LbsMapBo;

import java.util.Optional;

public interface LbsMapService {

    /**
     * 获取地图城市编码和地区编码
     *
     * @param location
     * @return
     */
    public Optional<LbsMapBo> getLbsInfoByLocation(String location);

    /**
     * 获取地域信息根据经纬度
     *
     * @param lng
     * @param lat
     * @return
     */
    public Optional<LbsMapBo> getLbsInfoByLonAndLat(String lng, String lat);


}
