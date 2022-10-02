package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditPayRequest(
    val fieldTrainingPayPerMonth: Long?,
    val editFullTimeEmploymentPay: EditEmploymentPayRequest?

)
