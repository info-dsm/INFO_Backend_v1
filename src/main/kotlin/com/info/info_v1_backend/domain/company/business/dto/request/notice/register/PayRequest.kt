package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.EmploymentPayRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Pay
import javax.validation.Valid


data class PayRequest(
    val fieldTrainingPayPerMonth: Long,
    @field:Valid
    val fullTimeEmploymentPay: EmploymentPayRequest
) {
    fun toPay(): Pay {
        return Pay(
            this.fieldTrainingPayPerMonth,
            this.fullTimeEmploymentPay.yearPayStart,
            this.fullTimeEmploymentPay.yearPayEnd,
            this.fullTimeEmploymentPay.bonus
        )
    }
}
