package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.EmploymentPayRequest


data class PayRequest(
    val fieldTrainingPayPerMonth: Long,
    val fullTimeEmploymentPay: EmploymentPayRequest
)
