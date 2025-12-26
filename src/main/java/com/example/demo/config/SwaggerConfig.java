package com.example.demo.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Crop & Fertilizer API")
                .description("Spring Boot REST API")
                .version("1.0"));
    }
}