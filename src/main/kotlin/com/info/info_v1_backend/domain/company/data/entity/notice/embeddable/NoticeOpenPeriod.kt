package com.info.info_v1_backend.domain.company.data.entity.notice.embeddable

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.NoticeOpenPeriodRequest
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

    fun toNoticeOpenPeriod(): NoticeOpenPeriodRequest {
        return NoticeOpenPeriodRequest(
            this.startDate,
            this.endDate
        )
    }

}