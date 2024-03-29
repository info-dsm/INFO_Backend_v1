package com.info.info_v1_backend.domain.auth.business.dto.response

import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class CompanyResponse(
        override val name: String,
        override val email: String,
        val contactorRank: String?,
        val contactorPhone: String?,
        val company: MinimumCompanyResponse?
): UserInfoResponse()