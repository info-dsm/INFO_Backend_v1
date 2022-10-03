package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.Pattern

data class EditPasswordRequest(
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$", message = "비밀번호는 영소문자,대문자,숫자,특수문자 8~30자여야 합니다.")
    val password: String,
    val code: String
)