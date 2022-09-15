package com.info.info_v1_backend.domain.auth.presentation.dto.request

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class StudentSignUpRequest(

    @Pattern(regexp = "^[123][1-5][012]\\d\$")
    val studentKey: String,

    @Pattern(regexp = "[a-zA-Z0-9+\\_.]+@dsm\\.hs\\.kr\$")
    val email: String,

    @Size(min = 4, max = 4)
    val emailCheckCode: String,

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$")
    val password: String,

    @NotNull
    @Size(min = 2, max = 4, message="2 ~ 4글자")
    val name: String,
    val userType: Role,

    @NotNull
    val githubLink: String
)
