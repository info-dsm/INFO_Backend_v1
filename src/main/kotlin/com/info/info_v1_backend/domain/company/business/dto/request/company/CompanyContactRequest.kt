package com.info.info_v1_backend.domain.company.business.dto.request.company

import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyContact
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class CompanyContactRequest(
    val contactorName: String,
    val contactorRank: String?,
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val phoneNumber: String,
    @field:Pattern(
        regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$",
        message = "전화번호는 반드시 02 or 0xx-xxxx-xxxx 조합이여야합니다."
    )
    val contactorPhone: String?,
    @field:Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]\$",message = "올바른 이메일 형식이 아닙니다.")
    val email: String,
) {
    fun toCompanyContact(): CompanyContact {
        return CompanyContact(
            this.contactorName,
            this.contactorRank,
            this.phoneNumber,
            this.contactorPhone,
            this.email
        )
    }
}
