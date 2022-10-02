package com.info.info_v1_backend.domain.auth.business.dto.request

data class ChangeEmailRequest(
        val email: String,
        val password: String,
)
