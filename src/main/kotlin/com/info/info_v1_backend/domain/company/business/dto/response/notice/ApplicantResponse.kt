package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.auth.business.dto.response.MinimumStudent
import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter

data class ApplicantResponse(
    val student: MinimumStudent,
    val reporterList: List<Reporter>
)
