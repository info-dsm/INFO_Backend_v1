package com.info.info_v1_backend.global.security.jwt.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConfigurationProperties("jwt")
@ConstructorBinding
data class JwtProperty(
    val secretKey: String,
    val accessExpiredAt: Long,
    val refreshExpiredAt: Long
)
