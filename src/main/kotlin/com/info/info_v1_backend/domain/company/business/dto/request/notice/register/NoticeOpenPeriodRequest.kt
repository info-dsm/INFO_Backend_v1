package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import java.time.LocalDate

data class NoticeOpenPeriodRequest (
    val startDate: LocalDate,
    val endDate: LocalDate
)