package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ChangeEmailRequest(
        @field:Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]\$",message = "올바른 이메일 형식이 아닙니다.")
        val email: String,
        val password: String,
)
