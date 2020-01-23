package com.dandinglong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MpLoginInterrupter()).addPathPatterns("/**")
                .excludePathPatterns("/upload/imgType")
                .excludePathPatterns("/upload/qiniuToken")
                .excludePathPatterns("/ocr/*")
                .excludePathPatterns("/user/onLogin");
    }
}
