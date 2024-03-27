package com.example.catdog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
}
