package com.smart.pantry.backend.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Apply to all endpoints under /api
                        .allowedOrigins("http://localhost:3000") // Allow your React app's origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed request methods
                        .allowedHeaders("*") // Allowed headers
                        .allowCredentials(true); // Allow credentials
            }
        };
    }
}
