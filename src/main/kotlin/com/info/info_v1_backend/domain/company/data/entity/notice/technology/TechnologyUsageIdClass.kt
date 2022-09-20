package com.info.info_v1_backend.domain.company.data.entity.notice.technology

import java.io.Serializable

class TechnologyUsageIdClass(
    technologyId: String,
    noticeId: Long
): Serializable {
    val technologyId: String = technologyId

    val noticeId: Long = noticeId
}