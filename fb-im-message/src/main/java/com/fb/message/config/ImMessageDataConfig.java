package com.fb.message.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@Slf4j
@MapperScan(value = "com.fb.message.dao", sqlSessionFactoryRef = "messageSqlSessionFactory")
public class ImMessageDataConfig {

    @Value("${message.datasource.url}")
    private String url;

    @Value("${message.datasource.driverClass}")
    private String driverClass;

    @Value("${message.datasource.password}")
    private String password;

    @Value("${message.datasource.username}")
    private String username;

    @Value("${message.datasource.maxActive}")
    private Integer maxActive;

    @Value("${message.datasource.minIdle}")
    private Integer minIdle;

    @Value("${message.datasource.validationQuery}")
    private String validationQuery;

    @Value("${message.datasource.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${message.datasource.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${message.datasource.queryTimeout}")
    private Integer queryTimeout;

    @Value("${message.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;


    @Bean(name = "messageDataSource", destroyMethod = "close")
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClass);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setQueryTimeout(queryTimeout);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return dataSource;
    }


    @Bean("messageSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("messageDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setGlobalConfig(globalConfig);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        Interceptor[] interceptors = null;
        interceptors = new Interceptor[]{new PaginationInterceptor()};

        sqlSessionFactory.setPlugins(interceptors);

        return sqlSessionFactory.getObject();
    }

    @Bean("messagePlatformTransactionManacger")
    public PlatformTransactionManager transactionManager(@Qualifier("messageDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
