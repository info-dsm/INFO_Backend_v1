package com.info.info_v1_backend.domain.company.business.dto.request.company

import javax.validation.constraints.Size

data class CompanyNameRequest(

    val companyNumber: String,
    @field:Size(max = 50, min = 1)
    val companyName: String

)