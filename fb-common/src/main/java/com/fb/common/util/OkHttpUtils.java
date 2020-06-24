package com.fb.common.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Slf4j
public class OkHttpUtils {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");


    //TODO LX
    public static String httpGetTryIfFailure(String url) throws IOException {
        int tryTime = 3;
        while (true && tryTime > 0) {
            String response = httpGet(url);
            if (StringUtils.isNotEmpty(response)) {
                return response;
            }
            tryTime--;
        }
        return "";
    }

    /**
     * Get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();

    }

    /**
     * Post请求
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, String json) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();

    }
}
