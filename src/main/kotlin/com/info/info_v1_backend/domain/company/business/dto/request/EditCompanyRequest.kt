package com.info.info_v1_backend.domain.company.business.dto.request

import java.time.Year

data class EditCompanyRequest (
    val shortName: String,
    val fullName: String,
    val contactor: ContactorDto,
    val companyPhoneNumber: String,
    val faxAddress: String,
    val establishedAt: Year,
    val annualSales: Long,
    val workerCount: Int,
    val industryType: String?,
    val mainProduct: String?,
    val introduction: String

)