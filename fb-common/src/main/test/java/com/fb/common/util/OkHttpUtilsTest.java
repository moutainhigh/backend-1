package com.fb.common.util;

import com.fb.common.BaseTest;
import org.junit.Test;

public class OkHttpUtilsTest extends BaseTest {
    @Test
    public void httpGet() throws Exception {

        String response = OkHttpUtils.httpGet("https://restapi.amap.com/v3/geocode/geo?address=%E5%8C%97%E4%BA%AC%E5%B8%82%E6%9C%9D%E9%98%B3%E5%8C%BA%E9%98%9C%E9%80%9A%E4%B8%9C%E5%A4%A7%E8%A1%976%E5%8F%B7&output=JSON&key=da7e4f958ab0ebca7e43d7b7b987fa9c");

        System.out.println(response);
    }
}
