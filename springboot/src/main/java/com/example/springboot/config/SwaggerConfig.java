package com.example.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAi(){
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
            .info (new Info().title(" Biblioteca Api ")
            .description("Api para gerenciamento de biblioteca")
            .version("v1.0.0"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
        

    }
    
}
