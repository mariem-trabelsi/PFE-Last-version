/*package com.foodsafety.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsFilter {

    public org.springframework.web.filter.CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin" , "Content-Type",
                "Accept", "Authorization", "Origin , Accept"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin" , "Content-Type",
                "Accept", "Authorization"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        org.springframework.web.filter.CorsFilter corsFilter = new org.springframework.web.filter.CorsFilter(urlBasedCorsConfigurationSource);
        return corsFilter;
    }
}*/