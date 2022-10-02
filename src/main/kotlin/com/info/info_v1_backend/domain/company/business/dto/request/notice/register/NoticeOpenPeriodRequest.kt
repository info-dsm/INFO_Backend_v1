package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.NoticeOpenPeriod
import java.time.LocalDate

data class NoticeOpenPeriodRequest (
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    fun toNoticeOpenPeriod(): NoticeOpenPeriod {
        return NoticeOpenPeriod(
            this.startDate,
            this.endDate
        )
    }
}