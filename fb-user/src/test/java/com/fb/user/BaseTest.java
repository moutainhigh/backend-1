package com.fb.user;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//使用改类启动spring容器
@ContextConfiguration(locations="classpath:application-user.xml")//加载对应的配置
public class BaseTest {


}
