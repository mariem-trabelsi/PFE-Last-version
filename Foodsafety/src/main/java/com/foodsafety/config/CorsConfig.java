package com.foodsafety.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200","http://localhost:8180" )
                .allowedHeaders("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .exposedHeaders("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}

