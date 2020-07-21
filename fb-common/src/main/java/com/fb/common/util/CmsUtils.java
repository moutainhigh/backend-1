package com.fb.common.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: pangminpeng
 * @create: 2020-07-05 15:41
 */
@Slf4j
public class CmsUtils {

    private static IAcsClient client;

    private static final String CMS_CACHE_CATEGORY = "cms_";


    static {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G3wcPmt6XVkHRNPnVPE", "xs3JA7OzkS6z0pMTf3eqZXJWfiY3wN");
        client = new DefaultAcsClient(profile);
    }


    private final static int TIME_OUT = 5;

    public static boolean sendVerifyCode( RedisUtils redisUtils, String phoneNumber) {
        //有短信验证码，则返回；无短信验证码，则返回
        try {
            if (Objects.nonNull(redisUtils.getCacheObject(CMS_CACHE_CATEGORY + phoneNumber))) {
                return false;
            }
            String verifyCode = getRandomVerifyCode(6);
            CommonRequest request = new CommonRequest();
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            // 接收短信的手机号码
            request.putQueryParameter("PhoneNumbers", phoneNumber);
            // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
            request.putQueryParameter("SignName", "findbeer");
            // 短信模板ID
            request.putQueryParameter("TemplateCode", "SMS_196147415");
            // 短信模板变量对应的实际值，JSON格式。
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");
            log.info("aliyun response:{}", client.getCommonResponse(request).getData());
            redisUtils.setCacheObject(CMS_CACHE_CATEGORY + phoneNumber, verifyCode, TIME_OUT, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("send verifyCode to phoneNumber:{} exception", phoneNumber,  e);
        }
        return true;
    }

    private static String getRandomVerifyCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    //检查是否在有效期内
    public static boolean checkVerifyCode(RedisUtils redisUtils, String phoneNumber, String verifyCode) {
        String exist = (String) redisUtils.getCacheObject(CMS_CACHE_CATEGORY + phoneNumber);
        return Objects.equals(verifyCode, exist);
    }
}
