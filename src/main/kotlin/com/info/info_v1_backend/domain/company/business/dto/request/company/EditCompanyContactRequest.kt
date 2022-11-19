package com.info.info_v1_backend.domain.company.business.dto.request.company

import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

@Validated
data class EditCompanyContactRequest(
    val contactorName: String?,
    val contactorRank: String?,
    @field:Valid
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or xxx-xxxx-xxxx 조합이여야합니다."
    )
    val phoneNumber: String?,
    @field:Valid
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or xxx-xxxx-xxxx 조합이여야합니다."
    )
    val contactorPhone: String?,
    @field:Valid
    @Email(message = "올바른 이메일형식이 아닙니다")
    val email: String?,
)
