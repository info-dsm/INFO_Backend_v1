package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditWorkTimeRequest(
    val untilCommuteStartTime: Int?,
    val untilCommuteEndTime: Int?,
    val workTimeForWeek: Int?
)
