package com.info.info_v1_backend.global.security.config

import com.info.info_v1_backend.global.error.filter.ExceptionFilter
import com.info.info_v1_backend.global.security.jwt.JwtFilter
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.auth.CustomAuthDetailsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class FilterConfiguration(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: CustomAuthDetailsService,
    private val objectMapper: ObjectMapper
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtFilter = JwtFilter(tokenProvider, customAuthDetailsService)
        val exceptionFilter = ExceptionFilter(objectMapper)
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionFilter, JwtFilter::class.java)
    }
}