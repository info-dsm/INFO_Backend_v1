package com.info.info_v1_backend.global.security.jwt.data

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
