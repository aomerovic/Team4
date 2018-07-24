package com.team4.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/css/**",
                "/images/**",
                "/js/**",
                "/font/**",
                "/webjars/**")
                .addResourceLocations(
                        "classpath:/static/css/",
                        "classpath:/static/images/",
                        "classpath:/static/js/",
                        "classpath:/META-INF/resources/webjars/"
                );
    }
}