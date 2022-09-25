package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class NoticeOpenPeriod(
    startDate: LocalDate,
    endDate: LocalDate
) {

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate = startDate

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate = endDate

}