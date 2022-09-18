package com.info.info_v1_backend.domain.company.business.dto.request.notice

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class CommuteTimeRequest (
    @field:Min(0, message ="CommuteTime은 0~24까지입니다.")
    @field:Max(24, message = "CommuteTime은 0~24까지입니다.")
    val startTime: Int,
    @field:Min(0, message ="CommuteTime은 0~24까지입니다.")
    @field:Max(24, message = "CommuteTime은 0~24까지입니다.")
    val endTime: Int
)
