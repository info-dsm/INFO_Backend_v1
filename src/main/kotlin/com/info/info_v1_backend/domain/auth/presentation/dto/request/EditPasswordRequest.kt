package com.info.info_v1_backend.domain.auth.presentation.dto.request

data class EditPasswordRequest(
    val password: String,
    val code: String
)