package com.fb.activty.service;

import com.fb.activty.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class DeptServiceTest extends BaseTest {

    @Resource
    private DeptService deptService;
    @Test
    public void test() {
        deptService.insert();
    }
    @Test
    public void selectByPage() {
        System.out.println("输出" + deptService.selectByPage());
    }

}
