package com.info.info_v1_backend.domain.auth.business.dto.request

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import javax.validation.Valid
import javax.validation.constraints.Size
data class CompanySignupRequest(
    @field:Valid
    val companyNameRequest: CompanyNameRequest,
    @field:Valid
    val companyInformation: CompanyInformationRequest,
    @field:Valid
    val companyContact: CompanyContactRequest,
    val businessAreaList: List<String>,
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String,
    val isLeading: Boolean,
    val password: String,

)
