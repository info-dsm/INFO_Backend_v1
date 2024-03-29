package com.info.info_v1_backend.domain.company.business.dto.request.company

import com.info.info_v1_backend.domain.company.data.entity.company.address.AddressInfo
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import javax.validation.constraints.Max
import javax.validation.constraints.Min


data class CompanyInformationRequest(
    val homeAddress: AddressInfo,
    val agentAddress: AddressInfo?,
    val representative: String,
    val establishedAt: Int,
    @field:Max(100000)
    val workerCount: Int,
    @field:Min(0) @field:Max(10000000000000)
    val annualSales: Long

) {
    fun toCompanyInformation(): CompanyInformation {
        return CompanyInformation(
            this.homeAddress,
            this.agentAddress,
            this.representative,
            this.establishedAt,
            this.workerCount,
            this.annualSales
        )
    }
}
