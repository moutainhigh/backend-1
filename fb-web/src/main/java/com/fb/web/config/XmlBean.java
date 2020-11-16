package com.fb.web.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:user-application.xml",  "classpath:activity-application.xml", "classpath:feed-application.xml", "classpath:message-application.xml", "classpath:addition-application.xml", "classpath:common-application.xml", "classpath:pay-application.xml", "classpath:order-application.xml", "classpath:user-relation-application.xml"})
public class XmlBean {

}



