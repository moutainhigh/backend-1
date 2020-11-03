package com.fb.user.relation;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 14:29
 */
@RunWith(SpringJUnit4ClassRunner.class)//使用改类启动spring容器
@ContextConfiguration(locations = "classpath:user-relation-application.xml")//加载对应的配置
public class BaseTest {
}
