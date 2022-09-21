package com.info.info_v1_backend.domain.auth.presentation.dto.request

import javax.validation.constraints.Pattern

data class ChangeEmailRequest(
        val email: String,
        @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$", message = "영문 숫자 특수문 조합이여야 하고 8자에서 30자 이만 가능합니다.")
        val password: String,
)
