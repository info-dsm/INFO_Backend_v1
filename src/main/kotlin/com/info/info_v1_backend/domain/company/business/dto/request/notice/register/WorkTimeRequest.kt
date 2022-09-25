package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class WorkTimeRequest(
    @field:Min(0, message ="CommuteTime은 0~24까지입니다.")
    @field:Max(24, message = "CommuteTime은 0~24까지입니다.")
    val untilCommuteStartTime: Int,
    @field:Min(0, message ="CommuteTime은 0~24까지입니다.")
    @field:Max(24, message = "CommuteTime은 0~24까지입니다.")
    val untilCommuteEndTime: Int,
    val workTimeForWeek: Int

)
