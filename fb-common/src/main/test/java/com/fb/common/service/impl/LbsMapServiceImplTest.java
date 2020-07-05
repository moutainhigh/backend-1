package com.fb.common.service.impl;


import com.fb.common.BaseTest;
import com.fb.common.model.LbsMapBo;
import com.fb.common.service.LbsMapService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class LbsMapServiceImplTest extends BaseTest {

    @Autowired
    private LbsMapService lbsMapService;

    @Test
    public void getLbsMapInfo() {
        Optional<LbsMapBo> lbsMapBo = lbsMapService.getLbsInfoByLocation("116.310003,39.991957");
        System.out.println("LbsMapBo=" + lbsMapBo.toString());
    }
}
