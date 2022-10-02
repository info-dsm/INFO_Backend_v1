package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.Pattern

data class EditPasswordRequest(
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$")
    val password: String,
    val code: String
)