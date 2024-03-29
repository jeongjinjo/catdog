package com.example.catdog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@OpenAPIDefinition(
        info = @Info(
                title = "CatDog BackEnd"
                , description = "기능에 대한 정보를 상세히 설명해드리겠습니다."
                , version = "v1.0.0"
        )
)

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {
        // SecurityScheme 설정
        String jwtSchemeName = "jwtToken";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)


        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement);
    }
}
