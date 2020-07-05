package com.fb.common.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class OssConfig {

    private static final int CONF_MAX_CONNECTIONS = 1024;
    private static final int CONF_SOCKET_TIMEOUT = 5000;
    private static final int CONF_CONNECTION_TIMEOUT = 50000;
    private static final int CONF_CONNECTION_REQUEST_TIMEOUT = 500;
    private static final int CONF_IDLE_CONNECTION_TIME = 6000;
    private static final int CONF_MAX_ERROR_RETRY = 3;


    @Value("${oss.endpoint}")
    private String OSS_ENDPOINT;

    @Value("${oss.access.key.id}")
    private String OSS_ACCESS_KEY_ID;

    @Value("${oss.access.key.secret}")
    private String OSS_ACCESS_KEY_SECRET;


    @Bean(name = "aliyunClientBuilderConfig")
    public ClientBuilderConfiguration initConf() {
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(CONF_MAX_CONNECTIONS);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(CONF_SOCKET_TIMEOUT);
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(CONF_CONNECTION_TIMEOUT);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        conf.setConnectionRequestTimeout(CONF_CONNECTION_REQUEST_TIMEOUT);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(CONF_IDLE_CONNECTION_TIME);
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(CONF_MAX_ERROR_RETRY);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        conf.setSupportCname(true);
        // 设置是否开启二级域名的访问方式，默认不开启。
        conf.setSLDEnabled(false);
        // 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTP);

        return conf;
    }

    //TOOD LX 注意销毁连接是否正确

    @Bean(value = "aliyunOSS", destroyMethod = "shutdown")
    public OSS ClientBuilderConfiguration(@Qualifier("aliyunClientBuilderConfig") ClientBuilderConfiguration config) throws Exception {

        return new OSSClientBuilder().build(OSS_ENDPOINT, OSS_ACCESS_KEY_ID, OSS_ACCESS_KEY_SECRET, config);
    }




}
