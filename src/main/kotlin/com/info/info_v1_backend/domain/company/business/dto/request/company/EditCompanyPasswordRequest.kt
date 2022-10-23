package com.info.info_v1_backend.domain.company.business.dto.request.company

import javax.validation.constraints.Pattern

data class EditCompanyPasswordRequest(
    @field:Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,30}\$",
        message = "비밀번호는 영소문자,대문자,숫자,특수문자 8~30자여야 합니다.")
    val password: String,
    val code: String,
    val email: String,
    val passwordHint: String
)
