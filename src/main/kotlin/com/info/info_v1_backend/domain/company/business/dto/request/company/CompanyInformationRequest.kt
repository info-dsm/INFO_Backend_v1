package com.info.info_v1_backend.domain.company.business.dto.request.company

import java.time.Year

data class CompanyInformationRequest(
    val homeAddress: String,
    val agentAddress: String?,
    val representative: String,
    val establishedAt: Year,
    val workerCount: Int,
    val annualSales: Long,

)
