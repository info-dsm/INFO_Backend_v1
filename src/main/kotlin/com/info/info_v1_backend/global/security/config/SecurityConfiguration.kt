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
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsUtils


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val customAuthDetailsService: CustomAuthDetailsService,
    private val objectMapper: ObjectMapper
): WebSecurityConfigurerAdapter() {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api-docs.json"
        , "/swagger-ui.html", "/dsql-api-docs/**",
        )
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
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
            .antMatchers(HttpMethod.POST, "/auth/email/school").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/signup/student").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/signup/teacher").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/company/email").permitAll()
            .antMatchers(HttpMethod.POST, "/company").permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(FilterConfiguration(tokenProvider, customAuthDetailsService, objectMapper))
    }

}