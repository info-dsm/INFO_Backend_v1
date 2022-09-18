package com.info.info_v1_backend.domain.auth.presentation.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)
