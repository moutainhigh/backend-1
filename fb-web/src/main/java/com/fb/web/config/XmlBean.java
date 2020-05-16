package com.fb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:application-user.xml","classpath:application-activty.xml"})
public class XmlBean {
}
