package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.data.entity.company.address.AddressInfo
import java.time.Year
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class CompanyInformation(
    homeAddress: AddressInfo,
    agentAddress: AddressInfo?,
    representative: String,
    establishedAt: Year,
    workerCount: Int,
    annualSales: Long
) {
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "full_address", column = Column(name = "home_full_address")),
        AttributeOverride(name = "address_number", column = Column(name = "home_address_number"))
    )
    var homeAddress: AddressInfo = homeAddress
        protected set

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "full_address", column = Column(name = "agent_full_address")),
        AttributeOverride(name = "address_number", column = Column(name = "agent_address_number"))
    )
    var agentAddress: AddressInfo? = agentAddress
        protected set

    @Column(name = "representative", nullable = false)
    var representative: String = representative
        protected set

    @Column(name = "established_at", nullable = false)
    var establishedAt: Year = establishedAt
        protected set

    @Column(name = "worker_count", nullable = false)
    var workerCount: Int = workerCount
        protected set

    @Column(name = "annual_sales", nullable = false)
    var annualSales: Long = annualSales
        protected set

    fun toCompanyInformationRequest(): CompanyInformationRequest {
        return CompanyInformationRequest(
            this.homeAddress,
            this.agentAddress,
            this.representative,
            this.establishedAt,
            this.workerCount,
            this.annualSales
        )
    }

}