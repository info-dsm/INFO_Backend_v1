package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class EmploymentPay(
    yearPayStart: Long,
    yearPayEnd: Long,
    bonus: Long?
) {
    @Column(name = "year_pay_start", nullable = false)
    var yearPayStart: Long = yearPayStart

    @Column(name = "year_pay_end", nullable = false)
    var yearPayEnd: Long = yearPayEnd

    @Column(name = "bonus", nullable = true)
    var bonus: Long? = bonus

}