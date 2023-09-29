package com.turingteam.config;

import com.turingteam.filter.CheckLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMVCConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new CheckLoginFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }
}
