package com.info.info_v1_backend.domain.company.business.dto.request.company

import com.info.info_v1_backend.domain.company.data.entity.company.address.AddressInfo
import com.info.info_v1_backend.domain.company.data.entity.company.embeddable.CompanyInformation
import javax.validation.constraints.Max
<<<<<<< Updated upstream
import javax.validation.constraints.Pattern
=======
import javax.validation.constraints.Min
import javax.validation.constraints.Size
>>>>>>> Stashed changes


data class CompanyInformationRequest(
    val homeAddress: AddressInfo,
    val agentAddress: AddressInfo?,
    val representative: String,
    val establishedAt: Int,
    @field:Max(100000)
    val workerCount: Int,
<<<<<<< Updated upstream
    @field:Pattern(regexp = "^\\d{1,20}\$")
    val annualSales: String
=======
    @field:Min(0) @field:Max(100000000000)
    val annualSales: Long
>>>>>>> Stashed changes

) {
    fun toCompanyInformation(): CompanyInformation {
        return CompanyInformation(
            this.homeAddress,
            this.agentAddress,
            this.representative,
            this.establishedAt,
            this.workerCount,
            this.annualSales.toLong()
        )
    }
}
