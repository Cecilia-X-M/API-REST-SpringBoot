package com.ejemplo.apirest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {

        return new OpenAPI()
                .info(new Info()
                        .title("API REST Clientes")
                        .description("API REST desarrollada con Spring Boot y MySQL")
                        .version("1.0.0"))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .security(
                        List.of(
                                new SecurityRequirement()
                                        .addList("bearerAuth")
                        )
                );
    }
}