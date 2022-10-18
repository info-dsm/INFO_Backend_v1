package com.info.info_v1_backend.global.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.info_v1_backend.global.security.jwt.TokenProvider
import com.info.info_v1_backend.global.security.jwt.auth.CustomAuthDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: CustomAuthDetailsService,
    private val objectMapper: ObjectMapper,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    @Bean
    @Throws(Exception::class)
    fun webSecurityCustomer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            it.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api-docs.json"
            , "/swagger-ui.html", "/dsql-api-docs/**")}
    }

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf().disable()
            .formLogin().disable()
            .cors()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//            .antMatchers(
//                "/api/**").anonymous()
            .antMatchers(HttpMethod.POST, "/api/info/v1/auth/email/school").permitAll()
            .antMatchers(HttpMethod.POST, "/api/info/v1/auth/signup/student").permitAll()
            .antMatchers(HttpMethod.POST, "/api/info/v1/auth/signup/teacher").permitAll()
            .antMatchers(HttpMethod.POST, "/api/info/v1/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/info/v1/company/email").permitAll()
            .antMatchers(HttpMethod.POST, "/api/info/v1/company").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(FilterConfiguration(tokenProvider, customAuthDetailsService, objectMapper))
            .and().build()
    }

}