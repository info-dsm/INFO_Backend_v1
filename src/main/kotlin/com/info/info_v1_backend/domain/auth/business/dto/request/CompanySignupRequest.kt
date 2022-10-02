package com.info.info_v1_backend.domain.auth.business.dto.request

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyIntroductionRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest

data class CompanySignupRequest(
    val companyNameRequest: CompanyNameRequest,
    val companyInformation: CompanyInformationRequest,
    val companyContact: CompanyContactRequest,
    val companyIntroduction: CompanyIntroductionRequest,
    val isLeading: Boolean,
    val password: String,

)
