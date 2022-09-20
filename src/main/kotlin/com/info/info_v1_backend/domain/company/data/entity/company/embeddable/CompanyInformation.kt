package com.info.info_v1_backend.domain.company.data.entity.company.embeddable

import java.time.Year
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class CompanyInformation(
    homeAddress: String,
    agentAddress: String?,
    representative: String,
    establishedAt: Year,
    workerCount: Int,
    annualSales: Long
) {
    @Column(name = "home_address", nullable = false)
    var homeAddress: String = homeAddress
        protected set

    @Column(name = "agent_address", nullable = true)
    var agentAddress: String? = agentAddress
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

}