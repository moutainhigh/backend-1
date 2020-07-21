package com.fb.message;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//使用改类启动spring容器
@ContextConfiguration(locations = "classpath:message-application.xml")//加载对应的配置
public class BaseTest {


}
