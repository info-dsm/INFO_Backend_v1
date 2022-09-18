package com.info.info_v1_backend.domain.company.business.dto.request.notice

import javax.validation.constraints.Min

data class EmploymentPayRequest(
    @field:Min(0, message = "yearPay는 0 이상이어야합니다.")
    val yearPay: Long?,
    @field:Min(0, message = "monthPay는 0 이상이어야합니다.")
    val monthPay: Long?,
    @field:Min(0, message = "bonus는 0 이상이어야합니다.")
    val bonus: Long?
)
