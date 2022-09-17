package com.info.info_v1_backend.domain.auth.presentation.dto.request

data class ContactorSignupRequest(
    val email: String,
    val emailCheckCode: String,
    val password: String,
    val contactorCheckCode: String,
    val name: String,
    val position: String,
    val personalPhone: String,

)
