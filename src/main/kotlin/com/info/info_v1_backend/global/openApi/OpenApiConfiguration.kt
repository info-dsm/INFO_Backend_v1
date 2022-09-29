package com.info.info_v1_backend.global.openApi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "Info-Backend-API-DOCS",

    )
)
@Configuration
class OpenApiConfiguration {

    @Bean
    fun authOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("인증")
            .pathsToMatch("/api/info/v1/auth/**")
            .build()

    }


    @Bean
    fun userOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("유저")
            .pathsToMatch("/api/info/v1/user/**")
            .build()

    }



    @Bean
    fun boardOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("취업현황")
            .pathsToMatch("/api/info/v1/board/**")
            .build()
    }

    @Bean
    fun companyOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("회사")
            .pathsToMatch("/api/info/v1/company/**")
            .build()
    }
     @Bean
    fun noticeOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("채용공고")
            .pathsToMatch("/api/info/v1/notice/**")
            .build()
    }

    @Bean
    fun projectOpenApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("프로젝트")
            .pathsToMatch("/api/info/v1/project/**")
            .build()
    }


}