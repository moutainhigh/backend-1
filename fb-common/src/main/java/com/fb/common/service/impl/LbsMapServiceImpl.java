package com.fb.common.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import com.fb.common.util.JsonUtils;
import com.fb.common.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class LbsMapServiceImpl implements LbsMapService {
    private final static String REGEOCODE = "regeocode";
    private final static String ADDRESS_COMPONENT = "addressComponent";
    private final static String CITYCODE = "citycode";
    private final static String ADCODE = "adcode";
    private final static String CITY = "city";
    private final static String DISTRICT = "district";
    private final static String PROVINCE = "province";


    @Value("${lbsKey}")
    public String lbsKey;

    @Value("${lbsUrl}")
    private String lbsUrl;

    @Override
    public Optional<LbsMapBo> getLbsInfoByLonAndLat(String lon, String lat) {
        if (StringUtils.isNotEmpty(lon) && StringUtils.isNotEmpty(lat)) {
            StringBuffer sb = new StringBuffer(lon).append(",").append(lat);
            return getLbsInfoByLocation(sb.toString());
        }
        return Optional.empty();
    }

    @Override
    public Optional<LbsMapBo> getLbsInfoByLocation(String location) {
        LbsMapBo lbsMapBo = new LbsMapBo();
        String url = new StringBuffer(lbsUrl)
                .append("key=")
                .append(lbsKey)
                .append("&location=")
                .append(location)
                .toString();

        try {
            String response = OkHttpUtils.httpGet(url);
            if (StringUtils.isNotEmpty(response)) {
                JsonNode lbsNode = JsonUtils.json2JsonNode(response);
                JsonNode addressNode = lbsNode.get(REGEOCODE).get(ADDRESS_COMPONENT);
                lbsMapBo.setCityCode(addressNode.get(CITYCODE).asText());
                lbsMapBo.setCityName(StringUtils.isEmpty(addressNode.get(CITY).asText()) ? addressNode.get(PROVINCE).asText() : addressNode.get(CITY).asText());

                lbsMapBo.setAdCode(addressNode.get(ADCODE).asText());
                lbsMapBo.setAdName(addressNode.get(DISTRICT).asText());
                lbsMapBo.setProvince(addressNode.get(PROVINCE).asText());
                log.info("getLbsMapInfo is location={},lbsNode ={},lbsMapBo={}", location, lbsNode, lbsMapBo);

            }
        } catch (IOException e) {
            log.error("getLbsMapInfo is error, location={}", location, e);
        }
        return Optional.ofNullable(lbsMapBo);
    }


}
