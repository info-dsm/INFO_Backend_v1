package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

import java.time.LocalDate

data class EditNoticeOpenPeriodRequest(
    val startDate: LocalDate?,
    val endDate: LocalDate?
)
