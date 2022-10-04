package com.info.info_v1_backend.domain.company.business.dto.request.company

import javax.validation.constraints.Pattern

data class EditCompanyContactRequest(
    val contactorName: String?,
    val contactorRank: String?,
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or xxx-xxxx-xxxx 조합이여야합니다."
    )
    val phoneNumber: String?,
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or xxx-xxxx-xxxx 조합이여야합니다."
    )
    val contactorPhone: String?,
    val email: String?,
)
