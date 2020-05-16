package com.fb.activty.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@Slf4j
@MapperScan("com.fb.activty.dao")
public class DataConfig {
    /**
     * mybatis-plus 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

    @Value("${activty.datasource.url}")
    private String url;

    @Value("${activty.datasource.driverClass}")
    private String driverClass;

    @Value("${activty.datasource.password}")
    private String password;

    @Value("${activty.datasource.username}")
    private String username;

    @Value("${activty.datasource.maxActive}")
    private Integer maxActive;

    @Value("${activty.datasource.minIdle}")
    private Integer minIdle;

    @Value("${activty.datasource.validationQuery}")
    private String validationQuery;

    @Value("${activty.datasource.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${activty.datasource.testWhileIdle}")
    private Boolean testWhileIdle;

    @Value("${activty.datasource.queryTimeout}")
    private Integer queryTimeout;

    @Value("${activty.datasource.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;

//    @Value("${activty.datasource.mapperLocations}")
//    private String mapperLocations;

    @Bean(destroyMethod = "close")
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


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, GlobalConfiguration globalConfiguration) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setGlobalConfig(globalConfiguration);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources(mapperLocations);
//        sqlSessionFactory.setMapperLocations(resources);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        Interceptor[] interceptors = null;
        interceptors = new Interceptor[]{new PaginationInterceptor()};

        sqlSessionFactory.setPlugins(interceptors);

        return sqlSessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration();
        conf.setIdType(1);
        conf.setCapitalMode(true);
        return conf;
    }

}
