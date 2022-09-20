package com.info.info_v1_backend.domain.company.business.dto.request.company

data class CompanyContactRequest(
    val contactorName: String,
    val contactorRank: String?,
    val phoneNumber: String,
    val contactorPhone: String?,
    val email: String
)
