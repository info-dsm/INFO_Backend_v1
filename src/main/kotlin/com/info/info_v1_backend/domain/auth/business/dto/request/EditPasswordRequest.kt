package com.info.info_v1_backend.domain.auth.business.dto.request

data class EditPasswordRequest(
    val password: String,
    val code: String
)