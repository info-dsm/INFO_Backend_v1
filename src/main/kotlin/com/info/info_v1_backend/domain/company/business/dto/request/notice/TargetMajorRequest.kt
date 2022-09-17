package com.info.info_v1_backend.domain.company.business.dto.request.notice

import com.info.info_v1_backend.domain.company.data.entity.type.MajorType

data class TargetMajorRequest(
    val majorType: MajorType,
    val count: Int
)
