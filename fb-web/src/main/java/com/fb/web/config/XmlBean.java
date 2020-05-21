package com.fb.web.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:user-application.xml", "classpath:application-activity.xml", "classpath:application-feed.xml","classpath:message-application.xml"})
public class XmlBean {

}



