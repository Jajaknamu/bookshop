package com.bookshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** 경로로 들어오는 요청을 → C:/upload/ 폴더에서 찾도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/upload/");
    }
}
