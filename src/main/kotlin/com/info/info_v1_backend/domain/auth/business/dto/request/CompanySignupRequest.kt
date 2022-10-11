package com.info.info_v1_backend.domain.auth.business.dto.request

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyNameRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.BusinessAreaResponse
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Validated
data class CompanySignupRequest(
    @field:Valid
    val companyNameRequest: CompanyNameRequest,
    @field:Valid
    val companyInformation: CompanyInformationRequest,
    @field:Valid
    val companyContact: CompanyContactRequest,
    @field:NotNull
    val businessAreaList: List<String>,
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String,
    val isLeading: Boolean,
    @field:Valid
    @field:Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,30}\$",
        message = "비밀번호는 영소문자,대문자,숫자,특수문자 8~30자여야 합니다.")
    val password: String,

)
