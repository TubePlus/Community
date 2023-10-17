package com.example.community_service.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "TubePlus App API",
                version = "v1",
                description = "TubePlus App API Docs"
        )
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {

        String[] paths = {"/api/v1/community/**"};

        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch(paths)
                .build();
    }
}
