package com.info.info_v1_backend.domain.company.business.dto.request.notice


data class PayRequest(
    val fieldTrainingPay: Long,
    val employmentPay: EmploymentPayRequest
)
