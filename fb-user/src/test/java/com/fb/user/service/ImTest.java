package com.fb.user.service;

import com.fb.user.utils.CheckSumBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-12-19 12:14
 */
public class ImTest {

    @Test
    public void testCreateAccount() throws IOException {

        List<Long> nowUidList = Arrays.asList(33L, 45L, 39L, 37L, 41L, 44L, 2L, 1L);

        for (Long aLong : nowUidList) {
            CloseableHttpClient httpClient = HttpClients.custom().build();
            String url = "https://api.netease.im/nimserver/user/refreshToken.action";
            HttpPost httpPost = new HttpPost(url);
            String appKey ="67b35e65c41efd1097ef8504d5a88455";
            String appSecret = "941afbef9deb";
            String nonce = "12345";
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

            // 设置请求的header
            httpPost.addHeader("AppKey", appKey);
            httpPost.addHeader("Nonce", nonce);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            List<NameValuePair> nv = new ArrayList<>();
            nv.add(new BasicNameValuePair("accid", String.valueOf(aLong)));
            httpPost.setEntity(new UrlEncodedFormEntity(nv, "utf-8"));
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        }



    }
}
