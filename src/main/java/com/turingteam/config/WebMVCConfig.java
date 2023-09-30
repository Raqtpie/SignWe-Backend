package com.turingteam.config;

import com.turingteam.filter.CheckLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CheckLoginFilter> filterRegistrationBean(){
        FilterRegistrationBean<CheckLoginFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new CheckLoginFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 允许所有来源跨域访问
                .allowedMethods("*") // 允许所有HTTP方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true); // 允许携带跨域凭据
    }
}
