package com.info.info_v1_backend.domain.auth.business.dto.request

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import javax.validation.constraints.Size

data class CompanySignupRequest(
    val companyNameRequest: CompanyNameRequest,
    val companyInformation: CompanyInformationRequest,
    val companyContact: CompanyContactRequest,
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String,
    val isLeading: Boolean,
    val password: String,

)
