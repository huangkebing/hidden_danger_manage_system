package com.hkb.hdms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huangkebing
 * 2021/03/26
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String bpmnPath = System.getProperty("user.dir") + "/bpmn/";

        registry.addResourceHandler("/bpmn/**")
                .addResourceLocations("file:" + bpmnPath);

        String filePath = System.getProperty("user.dir") + "/upload/";

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + filePath);
    }
}
