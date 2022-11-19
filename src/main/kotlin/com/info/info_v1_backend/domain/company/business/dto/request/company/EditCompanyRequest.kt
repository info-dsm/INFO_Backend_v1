package com.info.info_v1_backend.domain.company.business.dto.request.company

import javax.validation.Valid
import javax.validation.constraints.Size


data class EditCompanyRequest (
    val companyName: String?,
    @field:Valid
    val companyInformation: EditCompanyInformationRequest?,
    @field:Valid
    val companyContact: EditCompanyContactRequest?,
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String?,
    val isLeading: Boolean?,


)