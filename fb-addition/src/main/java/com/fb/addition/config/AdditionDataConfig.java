package com.fb.addition.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
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
import java.util.Date;


@Configuration
@Slf4j
@MapperScan(value = "com.fb.addition.dao", sqlSessionFactoryRef = "additionSqlSessionFactory")
public class AdditionDataConfig {

    @Value("${addition.datasource.url}")
    private String url;

    @Value("${addition.datasource.driverClass}")
    private String driverClass;

    @Value("${addition.datasource.password}")
    private String password;

    @Value("${addition.datasource.username}")
    private String username;

    @Value("${addition.datasource.maxActive}")
    private Integer maxActive;

    @Value("${addition.datasource.minIdle}")
    private Integer minIdle;

    @Value("${addition.datasource.validationQuery}")
    private String validationQuery;

    @Value("${addition.datasource.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${addition.datasource.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${addition.datasource.queryTimeout}")
    private Integer queryTimeout;

    @Value("${addition.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

//    @Value("${addition.datasource.mapperLocations}")
//    private String mapperLocations;

    @Bean(name = "additionDataSource", destroyMethod = "close")
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


    @Bean("additionSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("additionDataSource") DataSource dataSource, @Qualifier("additionGlobalConfig") GlobalConfig globalConfig) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setGlobalConfig(globalConfig);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        Interceptor[] interceptors = null;
        interceptors = new Interceptor[]{new PaginationInterceptor()};

        sqlSessionFactory.setPlugins(interceptors);

        return sqlSessionFactory.getObject();
    }

    @Bean("additionPlatformTransactionManacger")
    public PlatformTransactionManager transactionManager(@Qualifier("additionDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("additionGlobalConfig")
    public GlobalConfig globalConfiguration() {
        // 全局配置文件
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                setFieldValByName("createTime", new Date(), metaObject);
                setFieldValByName("updateTime", new Date(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                setFieldValByName("updateTime", new Date(), metaObject);

            }
        });

        return globalConfig;
    }


}
