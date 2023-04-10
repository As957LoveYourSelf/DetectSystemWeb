package com.postdesign.detectsystem.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public WebInterceptor webInterceptor(){
        return new WebInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(webInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**/login"
                        ,"/**/applogin"
                        ,"/**/userManagePage/psdConfirm","/**/*.jpg","/**/*.png");
    }
}
