package com.info.info_v1_backend.domain.company.business.dto.response.company

import java.time.LocalDate

data class HiredStudentResponse(
    val studentId: Long,
    val studentName: String,
    val studentKey: String,
    val companyId: Long,
    val startDate: LocalDate,
)
