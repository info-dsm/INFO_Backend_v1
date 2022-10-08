package com.info.info_v1_backend.domain.company.business.dto.request.company

import com.info.info_v1_backend.domain.company.data.entity.company.address.AddressInfo
import java.time.Year
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class EditCompanyInformationRequest(
    val homeAddress: AddressInfo?,
    val agentAddress: AddressInfo?,
    val representative: String?,
    val establishedAt: Int?,
    @field:Max(100000)
    val workerCount: Int?,
    @field:Min(0) @field:Max(10000000000000)
    val annualSales: Long?
)
