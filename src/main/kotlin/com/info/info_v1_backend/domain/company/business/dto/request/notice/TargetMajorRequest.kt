package com.info.info_v1_backend.domain.company.business.dto.request.notice

import com.info.info_v1_backend.domain.company.data.entity.type.MajorType
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class TargetMajorRequest(
    val majorType: MajorType,
    @field:Min(1, message = "major count 최저는 1입니다.")
    @field:Max(30, message = "major count에는 최대 30까지 입력할 수 있습니다.")
    val count: Int
)
