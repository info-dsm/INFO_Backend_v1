package com.info.info_v1_backend.domain.auth.presentation.dto.request

import com.info.info_v1_backend.domain.auth.data.entity.type.Role
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class TeacherSingUpRequest(

    @field:Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm\\.hs\\.kr\$")
    val email: String,

    @field:Size(min = 4, max = 4)
    val emailCheckCode: String,

    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$")
    val password: String,

    @field:NotNull
    val teacherCheckCode: String,

    @field:NotNull
    @field:Size(min = 2, max = 4, message="2 ~ 4글자")
    val name: String,
)
