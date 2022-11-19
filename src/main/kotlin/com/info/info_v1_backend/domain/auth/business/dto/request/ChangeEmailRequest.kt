package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.Email

data class ChangeEmailRequest(
        @field:Email(message = "올바른 이메일 형식이 아닙니다.")
        val email: String,
        val password: String,
        val code: String
)
