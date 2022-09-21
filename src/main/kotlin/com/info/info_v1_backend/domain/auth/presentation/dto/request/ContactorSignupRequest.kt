package com.info.info_v1_backend.domain.auth.presentation.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.Size
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ContactorSignupRequest(
    @field:Size(max = 4, min = 1)
    val name: String,
    @field:Size(max = 10, message = "position은 10자 이하여야합니다.")
    val position: String,
    @field:NotNull
    @field:Pattern(
        regexp = "/^(o2|0[0-9]{2})-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})\$/",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val phoneNum: String,
    @field:Email(message = "이메일 형식이 아닙니다.")
    val email: String,
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#\$%^&*()+|=]{8,30}\$", message = "비밀번호는 영소문자,대문자,숫자,특수문자 8~30자여야 합니다.")
    val password: String,
    val emailCheckCode: String,
    val contactorCheckCode: String,
    val personalPhone: String,

)
