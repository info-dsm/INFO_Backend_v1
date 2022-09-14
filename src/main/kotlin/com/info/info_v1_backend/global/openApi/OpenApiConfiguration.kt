package com.info.info_v1_backend.global.openApi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "DSQL-Backend-API-DOCS",

    )
)
@Configuration
class OpenApiConfiguration {

    @Bean
    fun newsOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("학교 교지")
            .pathsToMatch("/api/dsql/v1/news/**")
            .build()

    }

    @Bean
    fun postOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("포스팅")
            .pathsToMatch("/api/dsql/v1/post/**")
            .build()
    }

    @Bean
    fun authOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("유저")
            .pathsToMatch("/api/dsql/v1/auth/**")
            .build()
    }

    @Bean
    fun projectOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("프로젝트")
            .pathsToMatch("/api/dsql/v1/project/**")
            .build()
    }


}