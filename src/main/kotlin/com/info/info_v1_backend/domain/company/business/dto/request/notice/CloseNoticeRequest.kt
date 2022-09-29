package com.info.info_v1_backend.domain.company.business.dto.request.notice

import java.time.LocalDate

data class CloseNoticeRequest(
    val studentIdList: List<Long>,
    val fieldTrainingStartDate: LocalDate,
    val fieldTrainingEndDate: LocalDate
)
