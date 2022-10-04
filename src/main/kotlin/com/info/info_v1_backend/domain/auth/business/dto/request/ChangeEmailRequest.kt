package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ChangeEmailRequest(
        @field:NotBlank(message = "공백이거나 NULL값이 올 수 없습니다.")
        @field:Email(message = "올바른 이메일 형식이 아닙니다.")
        val email: String,
        val password: String,
)
