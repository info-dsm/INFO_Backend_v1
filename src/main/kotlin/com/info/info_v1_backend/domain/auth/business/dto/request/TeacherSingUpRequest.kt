package com.info.info_v1_backend.domain.auth.business.dto.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class TeacherSingUpRequest(

    @field:Pattern(regexp = "[a-zA-Z\\d+_.]+@dsm\\.hs\\.kr\$", message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    val emailCheckCode: String,

    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$",
        message = "비밀번호는 영소문자,대문자,숫자,특수문자 8~30자여야 합니다.")
    val password: String,

    @field:NotNull
    val teacherCheckCode: String,

    @field:NotNull
    @field:Size(min = 2, max = 4, message="2 ~ 4글자이여야 합니다.")
    val name: String,
)
