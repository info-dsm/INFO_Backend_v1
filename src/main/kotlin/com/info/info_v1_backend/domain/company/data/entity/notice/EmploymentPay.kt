package com.info.info_v1_backend.domain.company.data.entity.notice

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class EmploymentPay(
    @Column(name = "year_pay", nullable = true)
    var yearPay: Long?,
    @Column(name = "month_pay", nullable = true)
    var monthPay: Long?,
    @Column(name = "bonus", nullable = true)
    var bonus: Long?
) {

}