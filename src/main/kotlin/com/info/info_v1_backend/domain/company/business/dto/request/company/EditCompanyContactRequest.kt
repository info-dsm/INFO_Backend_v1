package com.info.info_v1_backend.domain.company.business.dto.request.company

import javax.validation.constraints.Pattern

data class EditCompanyContactRequest(
    val contactorName: String?,
    val contactorRank: String?,
    @field:Pattern(
        regexp = "/^(o2|0[0-9]{2})-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})\$/",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val phoneNumber: String?,
    @field:Pattern(
        regexp = "/^(o2|0[0-9]{2})-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})\$/",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val contactorPhone: String?,
    val email: String?,
)
