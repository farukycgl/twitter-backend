package com.tryst.twitter_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**") //tüm endpointlere izin ver.
                        .allowedOrigins("http://localhost:5173") // frontend portu.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTİONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
