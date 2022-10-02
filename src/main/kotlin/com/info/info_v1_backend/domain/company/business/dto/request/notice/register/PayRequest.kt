package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.EmploymentPayRequest
import com.info.info_v1_backend.domain.company.data.entity.notice.Pay


data class PayRequest(
    val fieldTrainingPayPerMonth: Long,
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
