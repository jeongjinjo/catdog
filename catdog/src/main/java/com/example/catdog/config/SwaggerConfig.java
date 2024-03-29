package com.example.catdog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;

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
        public Docket api() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.example.catdog"))
                        .build()
                        .securitySchemes(Arrays.asList(apiKey()));
        }

        private ApiKey apiKey() {
                return new ApiKey("JWT", "Authorization", "header");
        }
        @Bean
        public SecurityConfiguration security() {
                return SecurityConfigurationBuilder.builder()
                        .scopeSeparator(",")
                        .additionalQueryStringParams(null)
                        .useBasicAuthenticationWithAccessCodeGrant(false)
                        .build();
        }

}
